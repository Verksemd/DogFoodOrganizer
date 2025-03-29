import org.example.Calculator
import java.time.LocalDate

class Notifier<T>(
    private val calculator: Calculator,
    private val sender:Output<T>
) {
    fun informAboutFinishDate(recipient: Any): T {
        return sender.send(recipient, "The current dog food package will end on ${calculator.endDate} It's better to order food on ${calculator.orderDate} so " +
                "it arrives on time")
    }

    fun checkAndNotify(today: LocalDate =  LocalDate.now()) {
        when (today) {
            calculator.orderDate -> println("It's time to order dog food!")
            calculator.endDate -> println("The dog food ends today")
            else -> println("No notifications for today")
        }
    }
}