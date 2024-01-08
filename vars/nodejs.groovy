def call() {
    node('workstation') {
        common.compileCode('nodejs')
    }
}