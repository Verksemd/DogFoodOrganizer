import kotlinx.serialization.json.Json
import org.example.Calculator
import java.io.File
import java.time.LocalDate

fun askYesNo(prompt: String): Boolean {
    while (true) {
        print("$prompt (yes/no): ")
        when (readln().trim().lowercase()) {
            "yes", "y" -> return true
            "no", "n"  -> return false
            else       -> println("Please enter yes or no.")
        }
    }
}

fun collectPackageInfo(): FeedingPackageInput {
    var packageWeight = readDouble("Enter the package weight in kilograms: ")
    var numberOfFeedingsPerDay = readInt("Enter the number of feedings per day: ")
    var gramsPerCup = readDouble("Enter the grams per cup: ")
    var startDate = readDate("Enter the date when you bought the package (yyyy-MM-dd): ")
    return FeedingPackageInput(
        packageWeightKg = packageWeight,
        numberOfFeedingsPerDay = numberOfFeedingsPerDay,
        gramsPerCup = gramsPerCup,
        startDate = startDate.toString()
    )
}

fun savePackageInfo(data: FeedingPackageInput, path: String = "saved_package.json") {
    val json = Json.encodeToString(data)
    File(path).writeText(json)
}

fun main() {
    val savedFile = File("saved_package.json")
    val packageInfo: FeedingPackageInput = if (!savedFile.exists()) {
        println("Let's set up your dog's food package information.")
        val newInfo = collectPackageInfo()

        if (askYesNo("Do you want to save this package information?")) {
            savePackageInfo(newInfo)
        }
        newInfo
    } else {
        val savedInput = try {
            Json.decodeFromString<FeedingPackageInput>(savedFile.readText())
        } catch (e: Exception) {
            println("Sorry, we couldn't find the package information.")
            val newInfo = collectPackageInfo()
            savePackageInfo(newInfo)
            newInfo
        }
        println("- Found saved package info:")
        println("- Package weight: ${savedInput.packageWeightKg} kg")
        println("- Feedings per day: ${savedInput.numberOfFeedingsPerDay}")
        println("- Grams per cup: ${savedInput.gramsPerCup}")
        println("- Start date: ${savedInput.startDate}")

        if (askYesNo("Is this still your current package information?")) {
            savedInput
        } else {
            println("Let's enter your new package information.")
            val newInfo = collectPackageInfo()

            if (askYesNo("Do you want to overwrite the saved info?")) {
                savePackageInfo(newInfo)
                newInfo
            } else {
                savedInput
            }
        }
    }

    val consoleSender = ConsoleOutput()
    val sender = OutputAggregator(listOf(consoleSender))

    val calculator = Calculator(
        packageInfo.gramsPerCup,
        packageInfo.numberOfFeedingsPerDay,
        packageInfo.packageWeightKg,
        LocalDate.parse(packageInfo.startDate)
    )
    val notifier = Notifier(
        calculator,
        sender
    )
    notifier.informAboutFinishDate(1234)
}