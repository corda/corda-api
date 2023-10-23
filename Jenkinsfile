@Library('corda-shared-build-pipeline-steps@connelm/ES-1450/snyk-delta-retry') _

cordaPipelineKubernetesAgent(
    runIntegrationTests: false,
    runUnitTests: false,
    dependentJobsNames: [],
    dependentJobsNonBlocking: ['/Corda5/corda-api-compatibility/'],
    // always use -beta-9999999999999 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-beta-9999999999999',
    // allow publishing artifacts to S3 bucket
    publishToMavenS3Repository: true,
    javaVersion: '17'
    )
