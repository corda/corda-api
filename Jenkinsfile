@Library('corda-shared-build-pipeline-steps@jacob/ES-1398') _

cordaPipelineKubernetesAgent(
    runIntegrationTests: false,
    dependentJobsNames: [],
    dependentJobsNonBlocking: [],
    // always use -beta-9999999999999 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-beta-9999999999999',
    // allow publishing artifacts to S3 bucket
    publishToMavenS3Repository: true,
    javaVersion: '17'
    )
