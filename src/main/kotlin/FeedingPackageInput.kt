import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Serializable
data class FeedingPackageInput(
    val packageWeightKg: Double,
    val numberOfFeedingsPerDay: Int,
    val gramsPerCup: Double,
//    val startDate: LocalDate
    val startDate: String
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
    val formatters = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("dd.MM.yyyy"),
        DateTimeFormatter.ofPattern("MM-dd-yyyy"),                  // 01-01-2025 (в US-стиле)
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),                  // 01/01/2025
        DateTimeFormatter.ofPattern("MM/dd/yyyy")
    )
    while (true) {
        print(prompt)
        val input = readln()
        for (formatter in formatters) {
            try {
                return LocalDate.parse(input, formatter)
            } catch (e: DateTimeParseException) {
                continue
            }
        }
        println("Invalid date format. Try something like 2025-01-01 or 01-01-2025")

    }

}