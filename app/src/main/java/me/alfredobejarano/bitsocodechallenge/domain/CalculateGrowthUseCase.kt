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
        if (chart.size < 2) return 0.0
        // Take the last two items of the timeline.
        val lastDay = chart.sortedBy { it.date }.takeLast(2)
        // Take the last two chart points close price.
        val dayBefore = lastDay.first().closePrice
        val currentDay = lastDay.last().closePrice
        // Calculate the increment (or decrement) between the last two points in history.
        val increment = currentDay - dayBefore
        // Calculate the percentage.
        return (increment / dayBefore) * 100
    }
}