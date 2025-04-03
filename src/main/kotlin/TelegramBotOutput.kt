class TelegramBotOutput: Output {
    private lateinit var api: TelegramBot;

    fun init(tgAPI: TelegramBot) {
        api = tgAPI;
    }

    override fun send(recipient: Long, message: String) {
        api.send(recipient, message)
    }
}