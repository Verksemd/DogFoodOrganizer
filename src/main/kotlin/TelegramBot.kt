import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand
import com.github.kotlintelegrambot.entities.ChatId

class TelegramBot(
    private val notifier: Notifier,
    private var token: String
) {
    private lateinit var dispatcher: Dispatcher
    fun addHandler(command: String, handler: HandleCommand) {
        dispatcher.command(command, handler)
    }

    val bot: Bot = bot {
        token = this@TelegramBot.token
        dispatch {
            dispatcher = this
        }
    }

    fun send(chatId: Long, text: String) {
        bot.sendMessage(chatId = ChatId.fromId(chatId), text = text)
    }

    fun start() {
        bot.startPolling()
    }
}
