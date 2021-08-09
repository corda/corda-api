@Library('corda-shared-build-pipeline-steps@driessamyn/notick/support-version-suffix-override-for-local-publication') _

cordaPipeline(
    runIntegrationTests: false,
    nexusAppId: 'net.corda-api-5.0',
    dependentJobsNames: ['/Corda5/corda-runtime-os-version-compatibility/release%2Fent%2F5.0'],
    // always use -beta-0 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-beta-0'
    )