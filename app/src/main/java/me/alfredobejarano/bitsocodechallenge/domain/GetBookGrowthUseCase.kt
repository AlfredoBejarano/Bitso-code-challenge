package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import javax.inject.Inject

/**
 * GetBookChartUseCase
 */
class GetBookGrowthUseCase @Inject constructor() {
    fun getBookGrowth(chart: List<TradeChartPoint>): Double {
        val lastDay = chart.sortedBy { it.date }.takeLast(2)
        val difference = lastDay.first().closePrice - lastDay.last().closePrice
        return ((difference / lastDay.first().closePrice) * 100)
    }
}