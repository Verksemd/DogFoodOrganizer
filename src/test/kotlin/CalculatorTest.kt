import org.example.Calculator
import java.time.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest() {
    lateinit var calculator: Calculator
    @BeforeTest
    fun beforeEach() {
       calculator = Calculator(80.0,2,11.4, LocalDate.of(2025,1,25))
    }
    @Test
    fun testCalculateDailyConsumption() {
        assertEquals(160.0,calculator.dailyConsumption,"Daily Consumption doesn't match")
    }
    @Test
    fun testCalculateDaysOfSupply() {
     assertEquals(71, calculator.daysOfSupply, "Days of Supply don't match")
    }

}