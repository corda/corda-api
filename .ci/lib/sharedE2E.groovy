def call(String baseImage, String networkPrefix, def ranForWindows = false) {
    def nameSpace = UUID.randomUUID().toString()

    pipeline {
        agent {
            docker {
                // Our custom docker image
                image 'build-zulu-openjdk:11'
                label 'docker'
                registryUrl 'https://engineering-docker.software.r3.com/'
                registryCredentialsId 'artifactory-credentials'
                // Used to mount storage from the host as a volume to persist the cache between builds
                args '-v /tmp:/host_tmp'
                alwaysPull true
            }
        }

        environment {
            ARTIFACTORY_CREDENTIALS = credentials('artifactory-credentials')
            CORDA_ARTIFACTORY_USERNAME = "${env.ARTIFACTORY_CREDENTIALS_USR}"
            CORDA_ARTIFACTORY_PASSWORD = "${env.ARTIFACTORY_CREDENTIALS_PSW}"
            CORDA_USE_CACHE = "corda-remotes"
            GRADLE_USER_HOME = "/host_tmp/gradle"
            JIB_USER_HOME = "/host_tmp/jib"
            XDG_CACHE_HOME = "${env.JIB_USER_HOME}"
            CORDA_REVISION = "${env.GIT_COMMIT}"
            KUBECONFIG=credentials("e2e-tests-credentials")
            CORDA_CLI_USER_HOME="/tmp/corda-cli-home"
            NAME_SPACE = "${networkPrefix}${nameSpace}"
            BASE_IMAGE = "${baseImage}"
            E2E_TEST_NETWORK_NAME="${env.NAME_SPACE}"
            SMOKE_TEST_NETWORK_NAME="${env.NAME_SPACE}"
            DESTRUCTIVE_TEST_NETWORK_NAME="${env.NAME_SPACE}"
        }

        options {
            parallelsAlwaysFailFast()
            buildDiscarder(logRotator(daysToKeepStr: '14', artifactDaysToKeepStr: '14'))
            timeout(time: 40, unit: 'MINUTES')
            timestamps()
        }

        stages {
            stage('Prep') {
                steps {
                    sh 'mkdir -p "${GRADLE_USER_HOME}"'
                    sh 'mkdir -p "${JIB_USER_HOME}"'
                    sh 'mkdir -p "${CORDA_CLI_USER_HOME}"'
                }
            }
            stage('Build windows') {
                // Use premade image but still need our cordapps
                when { expression { return ranForWindows } }
                steps {
                    sh './gradlew -s prepareDeployment'
                }
            }
            stage('Build linux') {
                when { not { expression { return ranForWindows } } }
                steps {
                    sh './gradlew -s prepareDeployment'
                }
            }
            stage('Setup network windows') {
                // We can't use deploy image without running on a windows node
                when { expression { return ranForWindows } }
                steps {
                    sh 'corda-cli network config k8s "${NAME_SPACE}"'
                    sh 'cat testing/smoketests/src/smokeTest/resources/smoke-tests-network-ext-dbs.yaml | sed \'s/windows: false/windows: true/\' | sed \'s/proxy: true/proxy: false/\' | corda-cli network deploy -n "${NAME_SPACE}" -f /dev/stdin | kubectl apply -f /dev/stdin'
                    sh 'corda-cli network wait -n "${NAME_SPACE}" -t 20'
                    sh 'corda-cli network update -n ${NAME_SPACE} ./osgi-framework-bootstrap/build/libs/corda-5.0.0-SNAPSHOT.jar'
                    sh 'corda-cli network wait -n "${NAME_SPACE}" -t 10'
                    sh 'corda-cli package install -n ${NAME_SPACE} build/testDeployment/cordapps/*.cpk'
                    sh 'corda-cli network wait -n "${NAME_SPACE}" -t 10'
                }
            }
            stage('Setup network linux') {
                when { not { expression { return ranForWindows } } }
                steps {
                    sh 'corda-cli network config k8s "${NAME_SPACE}"'
                    sh 'corda-cli network build -n "${NAME_SPACE}" -b "${BASE_IMAGE}" -f testing/smoketests/src/smokeTest/resources/smoke-tests-network-ext-dbs.yaml build/testDeployment | kubectl apply -f -'
                }
            }
            stage('Compile the tests') {
                steps {
                    sh './gradlew compileSmokeTestKotlin compileSmokeTestJava compileE2eTestJava compileE2eTestKotlin compileDestructiveTestKotlin compileDestructiveTestJava'
                }
            }
            stage('Wait for Network') {
                steps {
                    sh 'corda-cli --stacktrace network wait -n "${NAME_SPACE}" -t 20'
                }
            }
            stage('Forward ports and run the tests windows') {
                when { expression { return ranForWindows } }
                steps {
                    sh '''
                        nohup corda-cli network forward -n "${NAME_SPACE}" > forward.txt 2>&1 &
                        procno=$! #remember process number started in background
                        trap "kill -9 ${procno}" EXIT
                        ./gradlew cleanTest cleanE2eTest cleanSmokeTest e2eTest smokeTest
                    '''
                }
            }
            stage('Forward ports and run the tests linux') {
                when { not { expression { return ranForWindows } } }
                steps {
                    sh '''
                        nohup corda-cli network forward -n "${NAME_SPACE}" > forward.txt 2>&1 &
                        procno=$! #remember process number started in background
                        trap "kill -9 ${procno}" EXIT
                        ./gradlew cleanTest cleanE2eTest cleanSmokeTest cleanDestructiveTest e2eTest smokeTest destructiveTest
                    '''
                }
                post {
                    always {
                        sh '''
            for POD in $(kubectl get pods -l type=corda -o name --namespace="${NAME_SPACE}" | cut -d'/' -f 2)
            do
              echo "${POD}"
              kubectl --namespace="${NAME_SPACE}" exec "${POD}" -- mkdir -p /home/corda/node
              kubectl --namespace="${NAME_SPACE}" exec "${POD}" -- sync
              kubectl --namespace="${NAME_SPACE}" exec "${POD}" -- rsync -a --inplace --exclude "*.jar" --exclude "*.tar.gz" --exclude "*.db" /opt/nodes /home/corda/node
              kubectl --namespace="${NAME_SPACE}" exec "${POD}" -- tar -czf /home/corda/"${POD}".tar.gz /home/corda/node
              echo "copying /home/corda/${POD}.tar.gz to $WORKSPACE/$POD.tar.gz"
              kubectl --namespace="${NAME_SPACE}" cp "${POD}:/home/corda/${POD}.tar.gz" "${WORKSPACE}/${POD}.tar.gz"
            done
            '''
                    }
                }
            }
        }
        post {
            success {
                sh 'corda-cli network terminate -yrf -n "${NAME_SPACE}"'
            }
            failure {
                sh '''
            for POD in $(kubectl get pods -o name --namespace="${NAME_SPACE}" | cut -d'/' -f 2)
            do
              echo "Collection container logs for ${POD}"
              kubectl logs ${POD} --all-containers --namespace="${NAME_SPACE}" > "${WORKSPACE}/${POD}.stout.log"
            done
            '''
                echo "You have 4hrs if you wish to connect to and triage '${env.NAME_SPACE}'"
                echo "For more info see: https://engineering.r3.com/engineering-central/how-we-work/build-logistics-and-tooling/build-and-test/test/eks-cluster-getting-started/"
            }
            cleanup {
                junit allowEmptyResults: true, testResults: '**/test-results/e2eTest/TEST-*.xml'
                archiveArtifacts artifacts: '**/test-results/e2eTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
                junit allowEmptyResults: true, testResults: '**/test-results/smokeTest/TEST-*.xml'
                archiveArtifacts artifacts: '**/test-results/smokeTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
                junit allowEmptyResults: true, testResults: '**/test-results/destructiveTest/TEST-*.xml'
                archiveArtifacts artifacts: '**/test-results/destructiveTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
                archiveArtifacts artifacts: 'forward.txt', allowEmptyArchive: true, fingerprint: true
                archiveArtifacts artifacts: "*.tar.gz", allowEmptyArchive: true, fingerprint: true
                archiveArtifacts artifacts: "*.stout.log", allowEmptyArchive: true, fingerprint: true
                sh 'rm -f *.tar.gz'
                sh 'rm -f *.stout.log'
                sh 'rm -f forward.txt'
            }
        }
    }
}

return this;
