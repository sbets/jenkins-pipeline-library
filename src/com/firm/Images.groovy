package com.firm

class Images implements Serializable {
    def steps

    Images(steps) {
        this.steps = steps
    }

    def buildAnImage(Map map = [:], String repositoryName, String releaseVersion) {
        steps.sh "docker build -t $repositoryName:$releaseVersion ."
    }

    def pushAnImage(String repositoryName, String releaseVersion) {
        steps.sh "docker push $repositoryName:$releaseVersion"
    }
}
