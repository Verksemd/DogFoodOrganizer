class ConsoleOutput: Output {
    override fun send(recipient: Long, message: String) {
        println("Recipient is $recipient \n" +
                "Message is $message ")
    }
}