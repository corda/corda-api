@Library('corda-shared-build-pipeline-steps@ronanb/CORE-3412/moving-nexus-scan') _

cordaPipeline(
    runIntegrationTests: false,
    dependentJobsNames: ['/Corda5/corda-runtime-os-version-compatibility/release%2Fent%2F5.0'],
    // always use -beta-9999999999999 for local publication as this is used for the version compatibility checks,
    //  This is a PR gate, so we want to check the "post merge" state before publication for real.
    localPublishVersionSuffixOverride: '-beta-9999999999999'
    )
