package com.firm

class DockerRegistryLogin implements Serializable {

    String loginToDockerRegistry(String registryUrl, String registryUsername, String registryPassword) {
        def command = "docker login $registryUrl --username $registryUsername --password $registryPassword"
        def proc = command.execute()

        proc.waitFor()
        return "${proc.in.text}"
    }
}
