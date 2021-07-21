def sharedE2E
node {
    checkout scm
    sharedE2E = load(".ci/lib/sharedE2E.groovy")
}
sharedE2E("adoptopenjdk-openj9-11-latest", "oj9-")
