import io.github.cdimascio.dotenv.dotenv
import org.example.Calculator
import java.time.LocalDate

fun main() {
    val dotenv = dotenv()

    val token = dotenv["TELEGRAM_BOT_TOKEN"] ?: error("No bot token")

    val tgSender = TelegramBotOutput()
    val consoleSender = ConsoleOutput()
    val sender = OutputAggregator(listOf(consoleSender, tgSender))

    val calculator = Calculator(80.0, 2, 11.4, LocalDate.of(2025, 1, 25))
    val notifier = Notifier(
        calculator,
        sender
    )

    // Telegram
    val telegramBot = TelegramBot(notifier, token)

    tgSender.init(telegramBot);

    telegramBot.start()
}