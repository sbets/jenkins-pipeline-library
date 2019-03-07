package com.firm

class ConfigureTiller implements Serializable {
    def steps

    ConfigureTiller(steps) {
        this.steps = steps
    }

    def setupTillerForDeploy() {
        steps.println("Setting up Tiller service account...")
        steps.sh "kubectl create serviceaccount tiller --namespace kube-system || exit 0"
        steps.sh "kubectl create clusterrolebinding tiller-admin-binding --clusterrole=cluster-admin --serviceaccount=kube-system:tiller || exit 0"
        steps.sh "kubectl create clusterrolebinding --clusterrole=cluster-admin --serviceaccount=default:default concourse-admin || exit 0"
    }
}