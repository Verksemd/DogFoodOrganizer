import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId


class TelegramBot(
    private val notifier: Notifier,
    private var token: String
) {
    val bot: Bot = bot {
        token = this@TelegramBot.token
        dispatch {

            command("start") {
                val chatId = message.chat.id
                send(chatId, "Привет! Я бот-кормилец 🐶. Доступные команды:\n" +
                        "/checkenddate — когда закончится корм\n" +
                "/checktoday - есть ли у собаки корм на сегодня")
            }

            command("checkenddate") {
                val chatId = message.chat.id
                notifier.informAboutFinishDate(chatId)
            }

            command("checktoday") {
                val chatId = message.chat.id
                notifier.checkToday(chatId)
          }
        }
    }

    fun send(chatId: Long, text: String) {
        bot.sendMessage(chatId = ChatId.fromId(chatId), text = text)
    }

    fun start() {
        bot.startPolling()
    }
}
