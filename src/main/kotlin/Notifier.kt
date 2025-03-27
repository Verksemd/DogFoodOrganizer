import org.example.Calculator

class Notifier(
    private val calculator: Calculator,
) {

    fun informAboutFinishDate() {
        println("The current dog food package will end on ${calculator.endDate} It's better to order food on ${calculator.orderDate} so " +
                "it arrives on time")
    }
}