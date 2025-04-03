package org.example

import java.time.LocalDate

class Calculator(
    var gramsPerCup: Double,
    val numberOfFeedingsPerDay: Int,
    var packageWeight: Double,
    var startDate: LocalDate
) {
    // How long it usually takes to deliver doog food
    private val daysForDelivery: Int = 4

    // How much food the dog usually eats in a day
    val dailyConsumption: Double
        get() = gramsPerCup * numberOfFeedingsPerDay

    // How many days are covered
    val daysOfSupply: Int
        get() = ((packageWeight * 1000) / dailyConsumption).toInt()

    // When the food runs out
    val endDate: LocalDate
        get() = startDate.plusDays(daysOfSupply.toLong())

    // When it's better to order food so the dog doesn't starve without it
    val orderDate: LocalDate
        get() = endDate.minusDays(daysForDelivery.toLong())
}