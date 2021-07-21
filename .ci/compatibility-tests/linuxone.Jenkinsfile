pipeline {
    agent {
        label 'linuxone'
    }

    environment {
        ARTIFACTORY_CREDENTIALS = credentials('artifactory-credentials')
        CORDA_ARTIFACTORY_USERNAME = "${env.ARTIFACTORY_CREDENTIALS_USR}"
        CORDA_ARTIFACTORY_PASSWORD = "${env.ARTIFACTORY_CREDENTIALS_PSW}"
        CORDA_USE_CACHE = "corda-remotes"
        GRADLE_USER_HOME = "/data/tmp/gradle"
        JIB_USER_HOME = "/data/tmp/jib"
        XDG_CACHE_HOME = "${env.JIB_USER_HOME}"
        CORDA_REVISION = "${env.GIT_COMMIT}"
        CORDA_CLI_USER_HOME="/data/tmp/corda-cli-home"
        E2E_TEST_NETWORK_NAME="smoke-tests-network"
        SMOKE_TEST_NETWORK_NAME="smoke-tests-network"
        DESTRUCTIVE_TEST_NETWORK_NAME="smoke-tests-network"
        PATH = "$PATH:/data/tmp/corda-cli/bin"
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
                sh '''
                curl -SLf -u $CORDA_ARTIFACTORY_USERNAME:$CORDA_ARTIFACTORY_PASSWORD "https://software.r3.com/artifactory/engineering-tools-maven/net/corda/cli/corda-cli-developer/[RELEASE]/corda-cli-developer-[RELEASE].tar;release.type+=RC" --output "/data/tmp/corda-cli.tar"
                rm -rf /data/tmp/corda-cli && mkdir /data/tmp/corda-cli
                tar -C /data/tmp/corda-cli --strip 1 -xf /data/tmp/corda-cli.tar
                corda-cli -v
                rm -rf /data/tmp/corda-cli.tar
                '''
            }
        }
        stage('Build') {
            steps {
                sh './gradlew -s prepareDeployment cleanTest cleanE2eTest cleanSmokeTest cleanDestructiveTest compileSmokeTestKotlin compileSmokeTestJava compileE2eTestJava compileE2eTestKotlin compileDestructiveTestKotlin compileDestructiveTestJava'
            }
        }
        stage('Setup network') {
            steps {
                withDockerRegistry(
                    credentialsId: 'artifactory-credentials',
                    url: "https://engineering-docker.software.r3.com"
                ) {
                    script {
                        sh 'cat testing/smoketests/src/smokeTest/resources/smoke-tests-network-ext-dbs.yaml | egrep -iv \'proxy:\\s+true|false\' | egrep -iv \'windows:\\s+true|false\' | egrep -iv \'database:\\s+.+\' > /data/tmp/corda-network.yml'
                        sh 'docker ps -aq | xargs docker container rm -fv || exit 0'
                        sh 'corda-cli config docker-compose smoke-tests-network'
                        sh 'docker pull --platform "linux/s390" engineering-docker.software.r3.com/corda-bootstrapper:linuxone-latest'
                        sh 'cat /data/tmp/corda-network.yml | JAVA_OPTS=-Djava.io.tmpdir=/data/tmp corda-cli deploy image smoke-tests-network -b linuxone-latest -f /dev/stdin build/testDeployment > /data/tmp/network.yml'
                        sh 'docker-compose -f /data/tmp/network.yml up -d'
                    }
                }
            }
        }
        stage('Wait for Network') {
            steps {
                sh 'corda-cli --stacktrace wait smoke-tests-network -t 20'
                sh 'docker-compose -f /data/tmp/network.yml logs'
            }
        }
        stage('Run the tests') {
            steps {
                sh './gradlew e2eTest smokeTest destructiveTest'
            }
        }
    }
    post {
        failure {
            sh 'docker-compose -f /data/tmp/network.yml logs | tee docker-compose.log'
            sh 'cat /data/tmp/corda-network.yml'
            sh 'cat /data/tmp/network.yml'
        }
        cleanup {
            junit allowEmptyResults: true, testResults: '**/test-results/e2eTest/TEST-*.xml'
            archiveArtifacts artifacts: '**/test-results/e2eTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
            junit allowEmptyResults: true, testResults: '**/test-results/smokeTest/TEST-*.xml'
            archiveArtifacts artifacts: '**/test-results/smokeTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
            junit allowEmptyResults: true, testResults: '**/test-results/destructiveTest/TEST-*.xml'
            archiveArtifacts artifacts: '**/test-results/destructiveTest/TEST-*.xml', allowEmptyArchive: true, fingerprint: true
            archiveArtifacts artifacts: "docker-compose.log", allowEmptyArchive: true, fingerprint: true
            archiveArtifacts artifacts: "/data/tmp/network.yml", allowEmptyArchive: true, fingerprint: true
            sh 'rm -f docker-compose.log'
            sh 'rm -f /data/tmp/network.yml'
            sh 'rm -f /data/tmp/corda-network.yml'
            sh 'corda-cli stop smoke-tests-network'
            sh 'corda-cli reset smoke-tests-network'
        }
    }
}