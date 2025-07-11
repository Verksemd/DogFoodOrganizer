import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.json.Json
import org.example.Calculator
import java.io.File
import java.time.format.DateTimeFormatter

class MyBotDraft {
    private var isWaitingForConfirmation = false
    private val dotenv = dotenv()
    private val token = dotenv["TELEGRAM_BOT_TOKEN"]
    val savedFile = File("saved_package.json")
    private val bot = bot {
        token = this@MyBotDraft.token
        dispatch {
            command("info") {
                if (isWaitingForConfirmation) {
                    deliverMessageToChat(message.chat.id, "Please, answer to previous question")
                    return@command
                }

                val savedInput = Json.decodeFromString<FeedingPackageInput>(savedFile.readText())
                var text = "- Found saved package info:" +
                        "- Package weight: ${savedInput.packageWeightKg}kg \n" +
                        "- Feedings/day: ${savedInput.numberOfFeedingsPerDay}\n " +
                        "- Grams per cup: ${savedInput.gramsPerCup}\n" +
                        "- Start date: ${savedInput.startDate}"

                val calculator = Calculator(savedInput)
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val end = calculator.endDate.format(formatter)
                val order = calculator.orderDate.format(formatter)
                text += "\nThe current dog food package will end on $end It's better to order food on $order so " +
                        "it arrives on time. Is it still valid? Yes/No"

                deliverMessageToChat(message.chat.id, text)

                isWaitingForConfirmation = true
            }
        }
    }

    fun deliverMessageToChat(chatId: Long, message: String) {
        bot.sendMessage(ChatId.fromId(chatId), message)
        println(message)
    }

    fun start() {
        bot.startPolling()
    }
}