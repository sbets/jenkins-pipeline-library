package com.firm.ossartifactory

import com.firm.DockerRegistryCredentials

@Grab('org.yaml:snakeyaml:1.17')
import org.yaml.snakeyaml.Yaml

class ArtifactoryRepoManager implements Serializable {
    def steps
    DockerRegistryCredentials credentialsArtifactory

    ArtifactoryRepoManager(steps) {
        this.steps = steps
        this.credentialsArtifactory = new DockerRegistryCredentials()
    }

    def getCredentialsArtifactory(String credentialsArtifactoryId) {
        String username = credentialsArtifactory.getUsername(credentialsArtifactoryId)
        String password = credentialsArtifactory.getPassword(credentialsArtifactoryId)
        def credMap = [user: "${username}", pwd: "${password}"]
        return credMap
    }

    def loginToArtifactory(String jenkinsCredentialsId, String urlArtifactory, String workSpace) {
        Map result = getCredentialsArtifactory(jenkinsCredentialsId)
        Map artifactInfo = getArtifactInfo(workSpace)
        steps.sh(script: "helm repo add helm ${urlArtifactory} --username ${result.user} --password ${result.pwd}", returnStdout: true)
        steps.println(result.user)
        uploadArtifact("${artifactInfo.name}-${artifactInfo.version}.tgz", "${urlArtifactory}", result.user, result.pwd)
    }

    def getArtifactInfo(path) {
        Yaml yaml = new Yaml()
        InputStream file = new FileInputStream("${path}/Chart.yaml")
        Object settingsTmp = yaml.load(file)
        Map<String, Object> settings = (Map<String, Object>) settingsTmp
        def artifactInfo = [name: "${settings.name}", version: "${settings.version}"]
        return artifactInfo
    }

    def uploadArtifact(String filename, String urlArtifactory, String user, String pwd) {
        steps.sh "curl -u${user}:${pwd} -T ${filename} \"${urlArtifactory}/${filename}\""
    }
}