package com.firm

class KubeSecret implements Serializable {
    def steps
    DockerRegistryCredentials dockerCreds

    KubeSecret(steps) {
        this.steps = steps
        this.dockerCreds = new DockerRegistryCredentials()
    }

    def addPrivateRegistryCredentials(String registry, String jenkinsCredentialsId) {
        String password = dockerCreds.getPassword(jenkinsCredentialsId)
        String username = dockerCreds.getUsername(jenkinsCredentialsId)
        steps.sh "kubectl create secret docker-registry regcred --docker-server=${registry} --docker-username=${username} --docker-password=${password} --docker-email=admin@example.com -o json | kubectl apply -f - || exit 0"
    }
}
