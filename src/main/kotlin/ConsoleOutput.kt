class ConsoleOutput: Output {
    override fun send(recipient: Long, message: String) {
        println("$message ")
    }
}