package org.example

import FeedingPackageInput
import java.time.LocalDate

class Calculator(
    private val feedingInfo: FeedingPackageInput
) {
    // How long it usually takes to deliver doog food
    private val daysForDelivery: Int = 4

    // How much food the dog usually eats in a day
    val dailyConsumption: Double
        get() = feedingInfo.gramsPerCup * feedingInfo.numberOfFeedingsPerDay

    // How many days are covered
    val daysOfSupply: Int
        get() = ((feedingInfo.packageWeightKg * 1000) / dailyConsumption).toInt()

    // When the food runs out
    val endDate: LocalDate
        get() = LocalDate.parse(feedingInfo.startDate).plusDays(daysOfSupply.toLong())

    // When it's better to order food so the dog doesn't starve without it
    val orderDate: LocalDate
        get() = endDate.minusDays(daysForDelivery.toLong())
}