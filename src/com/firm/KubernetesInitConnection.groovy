package com.firm

class KubernetesInitConnection implements Serializable {
    def steps

    KubernetesInitConnection(steps) {
        this.steps = steps
    }

    def initGoogleKubernetesConnection(String googleProject, String clusterName) {
        steps.println("Initialization connection to Google Kubernetes cluster...")
        steps.sh "gcloud auth activate-service-account --key-file=/tmp/credential_key.json"
        steps.sh "gcloud config set project ${googleProject}"
        steps.sh "gcloud container clusters get-credentials ${clusterName} --zone us-central1-a --project ${googleProject}"
        steps.sh "kubectl config use-context gke_${googleProject}_us-central1-a_${clusterName}"
    }
}