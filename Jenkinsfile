@Library('corda-shared-build-pipeline-steps@5.0') _

cordaPipeline(
    runIntegrationTests: false,
    nexusAppId: 'net.corda-api-5.0',
    // TODO: Revert this
    dependentJobsNames: ['/Corda5/corda-runtime-os-version-compatibility/PR-753'],
    // always use -beta-9999999999999 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-alpha-9999999999999'
    )