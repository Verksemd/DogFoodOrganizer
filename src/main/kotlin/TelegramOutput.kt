open class TelegramOutput(private val bot: Bot):Output {

    override fun send(recipient: Long, message: String) {
        try {
            bot.sendTelegramMessage(recipient, message)
        } catch (e: Exception) {
            println("Failed to send telegram message: ${e.message}")
        }

    }

}