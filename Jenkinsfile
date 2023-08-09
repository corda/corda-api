@Library('corda-shared-build-pipeline-steps@ES-593-Ensure-the-release-orchestrator-and-associated-publishing-logic-caters-to-grouping-GA-builds-in-S3') _

cordaPipeline(
    runIntegrationTests: false,
    dependentJobsNames: ['/Corda5/corda-runtime-os-version-compatibility/release%2Fos%2F5.0'],
    dependentJobsNonBlocking: ['/Corda5/corda-api-compatibility/'],
    // always use -beta-9999999999999 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-beta-9999999999999',
    // allow publishing artifacts to S3 bucket
    publishToMavenS3Repository: true,
    )
