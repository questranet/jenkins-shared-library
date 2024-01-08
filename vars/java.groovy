def call() {
    node('workstation') {
        common.codeCheckout()
        common.compileCode('java')
    }
}