def sharedE2E
node {
    checkout scm
    sharedE2E = load(".ci/lib/sharedE2E.groovy")
}
sharedE2E("corretto-11-latest", "cor-")
