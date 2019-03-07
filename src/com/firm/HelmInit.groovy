package com.firm

class HelmInit implements Serializable {
    def steps
    ConfigureTiller tiller

    HelmInit(steps) {
        this.steps = steps
        this.tiller = new ConfigureTiller(steps)
    }

    def initHelmCli() {
        steps.println("Initialization Helm connection to Google Kubernetes cluster...")
        tiller.setupTillerForDeploy()
        steps.sh "helm init --service-account=tiller"
        steps.sh "helm update"
        steps.sh "kubectl patch deploy --namespace kube-system tiller-deploy -p '{\"spec\":{\"template\":{\"spec\":{\"serviceAccount\":\"tiller\"}}}}' || exit 0"
        steps.sh "helm version"
    }

    def deployHelmChart(String appName, String tagVersion) {
        steps.println("Deploying application ${appName}")
        steps.sh "helm upgrade ${appName} . --set image.tag=${tagVersion} || helm install --name ${appName} . --set image.tag=${tagVersion}"
    }
}
