#!groovy



node() {
    checkout scm
    sh('env')
    try {
        config = readYaml(file: "config.yaml")
    } catch (e) {
        println("Unexpected error: ${e}")
        currentBuild.result = 'FAILED'
    }

    println config.weight
    
    stage('Fetch code and check ENV') {
        
        try {
          def tagVersion = sh(script: 'git tag -l --points-at HEAD', 
                              returnStdout: true).trim()
            println tagVersion
            if (tagVersion) {
                environment = "QA"
                println(environment)
            } else {
                environment = "DEV"
                println(environment)
                def BRANCH_NAME = "${env.BRANCH_NAME}"
                println BRANCH_NAME
            }
        } catch (e) {
            println(e)
        }            
    }
}
