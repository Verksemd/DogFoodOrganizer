import org.example.Calculator
import java.time.LocalDate

fun main() {
    val calculator = Calculator(80.0,2,11.4, LocalDate.of(2025,1,25))
    val notifier = Notifier(calculator)

    notifier.informAboutFinishDate()
}