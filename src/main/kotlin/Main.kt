import io.github.cdimascio.dotenv.dotenv
import org.example.Calculator
import java.time.LocalDate

fun main() {
    val dotenv = dotenv()
    val consoleSender = ConsoleOutput()
    val sender = OutputAggregator(listOf(consoleSender))
    // Reading the user's input
    val packageWeight = readDouble("Enter the package weight in kilograms: ")
    val numberOfFeedingsPerDay = readInt("Enter the number of feedings per day: ")
    val gramsPerCup = readDouble("Enter the grams per cup: ")
    val startDate = readDate("Enter the date when you bought the package (yyyy-MM-dd): ")

    val calculator = Calculator(gramsPerCup, numberOfFeedingsPerDay, packageWeight, startDate)
    val notifier = Notifier(
        calculator,
        sender
    )
    notifier.checkAndNotify(LocalDate.now())
    notifier.informAboutFinishDate(1234)
    /*
    Я пользователь
    Я вижу окно ввода информации о корме (вес корма, сколько раз кормим собаку в день, сколько граммов в мисочке, из которой она ест, дата покупки корма)
    Я нажимаю энтер
    Программа показывает сообщение с датой окончания корма и когда нужно заказть новую пачку.
     */
}