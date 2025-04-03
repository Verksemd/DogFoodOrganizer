import org.example.Calculator
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Мой нотифаер должен отправлять сообщения сам, в заивисимости от того,куда отправлять
// Сейчас он просто готовит сообщение, но не отправляет
// В первую очередь стоит избавиться от дженериков в интерфейсе и в нотифаере (остальные должны уйти сами)
class Notifier(
    private val calculator: Calculator,
    private val sender:Output // метод, которым мы отправляем
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val end = calculator.endDate.format(formatter)
    val order = calculator.orderDate.format(formatter)

    // recipient - это человек в тг, чат ид
    fun informAboutFinishDate(recipient: Long)  {
        return sender.send(recipient, "The current dog food package will end on $end It's better to order food on $order so " +
                "it arrives on time")
    }

    fun checkAndNotify(today: LocalDate =  LocalDate.now()) {
        when (today) {
            calculator.orderDate -> println("It's time to order dog food!")
            calculator.endDate -> println("The dog food ends today")
            else -> println("No notifications for today")
        }
    }
    fun checkToday(recipient: Long, today: LocalDate =  LocalDate.now()) {
        when (today) {
            calculator.orderDate -> return sender.send(recipient, "It's time to order dog food!")
            calculator.endDate -> return sender.send(recipient, "The dog food ends today")
            else -> return sender.send(recipient, "The dog still has food for today. It'll end on $end")
        }
    }




}