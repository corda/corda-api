def sharedE2E
node {
    checkout scm
    sharedE2E = load(".ci/lib/sharedE2E.groovy")
}
sharedE2E("adoptopenjdk-11-latest", "aoj-")
