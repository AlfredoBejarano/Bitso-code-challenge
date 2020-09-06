package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import javax.inject.Inject

/**
 * GetBookChartUseCase
 */
class CalculateGrowthUseCase @Inject constructor() {
    /**
     * Retrieves the grow percentage of the given book since the last 24 hours.
     * @param chart List of [TradeChartPoint] objects representing a certain amount of the price
     * timeline of the given book.
     * @return Percentage difference of the Book timeline.
     */
    fun getBookGrowth(chart: List<TradeChartPoint>): Double {
        // Take the last two items of the timeline.
        val lastDay = chart.takeLast(2)
        // Calculate the difference between the last known day and the day before.
        val difference = lastDay.first().closePrice - lastDay.last().closePrice
        // Calculate the difference percentage.
        return ((difference / lastDay.first().closePrice) * 100)
    }
}