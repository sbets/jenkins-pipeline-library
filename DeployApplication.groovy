#!groovy

try {
    def config = readYaml file: 'config.yaml'
} catch (e) {
    println("Unexpected error: ${e}")
    currentBuild.result = 'UNSTABLE'
}

//println config.weight

node() {
    checkout scm
    stage('Fetch code and check ENV') {
        
        sh 'env'
        def BRANCH_NAME = "${env.BRANCH_NAME}"
        println BRANCH_NAME
        try {
            tagVersion = sh(script: 'git tag -l --points-at HEAD', 
                              returnStdout: true).trim()
            println tagVersion
            if (tagVersion) {
                environment = "QA"
                println(environment)
            } else {
                environment = "DEV"
                println(environment)
            }
        } catch (e) {
            println(e)
        }            
    }
}
