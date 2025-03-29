import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId


class TelegramBot(
    private val notifier: Notifier<String>,
    private val chatId: Long,
    private var token: String
) {
    private val bot: Bot = bot {
        token = this@TelegramBot.token
    val output = TelegramBotOutput()
        dispatch {
            command("start") {
                send("Привет! Я бот-кормилец 🐶. Доступные команды:\n" +
                        "/checkenddate — когда закончится корм\n")
            }

            command("checkenddate") {
                val message = notifier.informAboutFinishDate(chatId)
                send(message)

            }

//            command("checkToday") {
//                val message = notifier.checkAndNotify()
//                send(message.toString())
//            }
        }
    }

    private fun send(text: String) {
        bot.sendMessage(chatId = ChatId.fromId(chatId), text = text)
    }

    fun start() {
        bot.startPolling()
    }
}
