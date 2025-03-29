class TelegramBotOutput: Output<String> {
    override fun send(recipient: Any, message: String): String {
        return message
    }
}