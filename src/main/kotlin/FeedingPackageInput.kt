import java.time.LocalDate
import java.time.format.DateTimeParseException

data class FeedingPackageInput(
    val packageWeightKg: Double,
    val numberOfFeedingsPerDay: Int,
    val gramsPerCup: Double,
    val startDate: LocalDate
)
fun readDouble(prompt: String): Double {
    while(true) {
        print(prompt)
        val input = readln()
        val number = input.toDoubleOrNull()
        if (number != null) return number
        println("Invalid number. Please enter a decimal number.")
    }
}

fun readInt(prompt: String): Int {
    while (true) {
        print(prompt)
        val input = readln()
        val number = input.toIntOrNull()
        if (number != null) return number
        println("Invalid number. Please enter an integer number.")
    }
}

fun readDate(prompt: String): LocalDate {
    while (true) {
        print(prompt)
        val input = readln()
        try {
            return LocalDate.parse(input)
        } catch (e: DateTimeParseException) {
            println("Invalid date format. Use yyyy-MM-dd.")
        }
    }
}