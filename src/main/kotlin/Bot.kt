import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import command.CheckToday
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.json.Json
import java.io.File


class Bot(
    private val notifier: Notifier,
) {
    private val dotenv = dotenv()
    private val token = dotenv["TELEGRAM_BOT_TOKEN"]
    private val telegramOutput = TelegramOutput(this)
    private val sender = OutputAggregator(listOf(telegramOutput))
    private val bot = bot {
        token = this@Bot.token
        dispatch {
            command("start") {
                val chatId = message.chat.id
                bot.sendMessage(
                    chatId = ChatId.fromId(chatId),
                    text = "👋 Welcome to Dog Food Tracker Bot!\n" + "Available commands:\n" + "/check - Check today's status\n" + "/info - Get information about current package"
                )
            }

            command("check") {
                CheckToday(notifier).invoke(this)
            }
            command("info") {

            }
        }
    }
    fun sendTelegramMessage(chatId: Long, text: String) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = text
        )
    }
    fun start() {
        println("Starting bot")
        try {
            bot.getMe()
            println("Bot connected")
            bot.startPolling()
            println("Bot polling started")
            val telegramSender = TelegramOutput(this)
            val sender = OutputAggregator(
                listOf(telegramSender)
            )
            val savedFile = File("saved_package.json")
            val packageInfo: FeedingPackageInput = if (!savedFile.exists()) {
                println("Let's set up your dog's food package information.")
                val newInfo = collectPackageInfo()
                calculateAndNotify(newInfo, sender)

                if (askYesNo("Do you want to save this package information?")) {
                    savePackageInfo(newInfo)
                    println("The current package information is saved")
                }
                // returing the result for the entered information but not saving it in the file
                println("The current package information will not be saved")
                newInfo
            } else {
                // loading the saved input
                val savedInput = try {
                    Json.decodeFromString<FeedingPackageInput>(savedFile.readText())
                } catch (e: Exception) {
                    println("Sorry, we couldn't find the package information.")
                    println("Let's enter new package information instead.")
                    val newInfo = collectPackageInfo()
                    calculateAndNotify(newInfo, sender)

                    if (askYesNo("Do you want to save this package information?")) {
                        savePackageInfo(newInfo)
                    }
                    newInfo
                }
                println("- Found saved package info:")
                println("- Package weight: ${savedInput.packageWeightKg} kg")
                println("- Feedings/day: ${savedInput.numberOfFeedingsPerDay}")
                println("- Grams per cup: ${savedInput.gramsPerCup}")
                println("- Start date: ${savedInput.startDate}")
                calculateAndNotify(savedInput, sender)

                if (askYesNo("Is this still your current package information?")) {
                    println("There is nothing else do to here")
                    savedInput
                } else {
                    println("Let's enter your new package information.")
                    val newInfo = collectPackageInfo()
                    calculateAndNotify(newInfo, sender)


                    if (askYesNo("Do you want to overwrite the saved info?")) {
                        savePackageInfo(newInfo)
                        println("The data has been overwritten")
                        newInfo
                    } else {
                        println("See you next time")
                        savedInput
                    }
                }
            }

        } catch (e: Exception) {
            println("Telegram bot failed to start")
            e.printStackTrace()
        }
    }
}