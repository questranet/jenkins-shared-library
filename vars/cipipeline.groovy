def call() {
    node('workstation') {
        common.codeCheckout()
        common.compileCode(appType)

        if(env.TAG_NAME ==~ ".*") {
            common.appRelease(appType)
        } else {
            stage('Code Quality') {
                sh 'echo OK'
            }
            stage('Unit Tests') {
                sh 'echo OK'
            }
        }
    }
}