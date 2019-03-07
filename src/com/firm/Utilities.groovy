package com.firm

class Utilities implements Serializable {
    def steps

    Utilities(steps) {
        this.steps = steps
    }

    def mvn(String cmd) {
        steps.println("Running $cmd...")
        steps.sh "$cmd"
    }
}
