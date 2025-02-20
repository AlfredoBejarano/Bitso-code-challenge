package me.alfredobejarano.bitsocodechallenge.model.mapper

import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.model.remote.TradeChartItem
import me.alfredobejarano.bitsocodechallenge.utils.asDate

/**
 * TradeChartItemToChartPointMapper
 */
class TradeChartItemToChartPointTradeChartPoint : Mapper<TradeChartItem, TradeChartPoint> {
    override fun map(item: TradeChartItem) = TradeChartPoint(
        closePrice = item.close?.toDouble() ?: 0.0,
        date = item.date.asDate("yyyy-MM-dd").time
    )
}