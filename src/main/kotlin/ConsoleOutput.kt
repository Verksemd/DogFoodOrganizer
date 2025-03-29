class ConsoleOutput: Output<Unit> {
    override fun send(recipient: Any, message: String) {
        println(message)
    }
}