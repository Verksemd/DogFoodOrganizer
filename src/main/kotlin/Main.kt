import io.github.cdimascio.dotenv.dotenv
import org.example.Calculator
import java.time.LocalDate

fun main() {
    val dotenv = dotenv()

    val token = dotenv["TELEGRAM_BOT_TOKEN"] ?: error("No bot token")
    val chatId = dotenv["TELEGRAM_CHAT_ID"]?.toLong() ?: error("No chat ID")

    val calculator = Calculator(80.0,2,11.4, LocalDate.of(2025,1,25))
    val notifier = Notifier(
        calculator,
        ConsoleOutput()
    )

    notifier.informAboutFinishDate(123)
//    val fakeToday = LocalDate.of(2025, 4, 2)
    notifier.checkAndNotify()

    // Telegram
    val notifierTelegram = Notifier(calculator, TelegramBotOutput())
    val telegramBot = TelegramBot(notifierTelegram, chatId, token)
    telegramBot.start()
}