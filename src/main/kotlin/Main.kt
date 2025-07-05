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
    val packageWeight = readDouble("Enter the package weight in kilograms: ")
    val numberOfFeedingsPerDay = readInt("Enter the number of feedings per day: ")
    val gramsPerCup = readDouble("Enter the grams per cup: ")
    val startDate = readDate("Enter the date when you bought the package (yyyy-MM-dd): ")
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

fun calculateAndNotify(newInfo: FeedingPackageInput, sender: Output) {
    val calculator = Calculator(
        newInfo.gramsPerCup,
        newInfo.numberOfFeedingsPerDay,
        newInfo.packageWeightKg,
        LocalDate.parse(newInfo.startDate)
    )
    val notifier = Notifier(calculator, sender)
    notifier.informAboutFinishDate(1234)
}

fun main() {
    println("🐶 Welcome to Dog Food Tracker!")
    val consoleSender = ConsoleOutput()
    val sender = OutputAggregator(listOf(consoleSender))
    val savedFile = File("saved_package.json")
    val packageInfo: FeedingPackageInput = if (!savedFile.exists()) {
        println("Let's set up your dog's food package information.")
        val newInfo = collectPackageInfo()
        calculateAndNotify(newInfo, sender)

        if (askYesNo("Do you want to save this package information?")) {
            savePackageInfo(newInfo)
            println("The current package information is saved")
        }
        // returing the result for the entered information but not saving it in the file
        println("The current package information will not be saved")
        newInfo
    } else {
        // loading the saved input
        val savedInput = try {
            Json.decodeFromString<FeedingPackageInput>(savedFile.readText())
        } catch (e: Exception) {
            println("Sorry, we couldn't find the package information.")
            println("Let's enter new package information instead.")
            val newInfo = collectPackageInfo()
            calculateAndNotify(newInfo, sender)

            if (askYesNo("Do you want to save this package information?")) {
                savePackageInfo(newInfo)
            }
            newInfo
        }
        println("- Found saved package info:")
        println("- Package weight: ${savedInput.packageWeightKg} kg")
        println("- Feedings/day: ${savedInput.numberOfFeedingsPerDay}")
        println("- Grams per cup: ${savedInput.gramsPerCup}")
        println("- Start date: ${savedInput.startDate}")
        calculateAndNotify(savedInput, sender)

        if (askYesNo("Is this still your current package information?")) {
            println("There is nothing else do to here")
            savedInput
        } else {
            println("Let's enter your new package information.")
            val newInfo = collectPackageInfo()
            calculateAndNotify(newInfo, sender)


            if (askYesNo("Do you want to overwrite the saved info?")) {
                savePackageInfo(newInfo)
                println("The data has been overwritten")
                newInfo
            } else {
                println("See you next time")
                savedInput
            }
        }
    }
}