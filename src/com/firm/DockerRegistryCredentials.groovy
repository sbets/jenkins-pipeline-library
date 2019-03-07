package com.firm

class DockerRegistryCredentials implements Serializable {

    String getPassword(String credentialsId) {
        def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                jenkins.model.Jenkins.instance
        )

        def c = creds.findResult { it.id == credentialsId ? it : null }
        if ( c ) {
            return c.password.getPlainText()
        } else {
            return "could not find credential for ${credentialsId}"
        }
    }

    String getUsername(String credentialsId) {
        def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials.class,
                jenkins.model.Jenkins.instance
        )

        def c = creds.findResult { it.id == credentialsId ? it : null }
        if ( c ) {
            return c.username
        } else {
            return "could not find credential for ${credentialsId}"
        }
    }
}
