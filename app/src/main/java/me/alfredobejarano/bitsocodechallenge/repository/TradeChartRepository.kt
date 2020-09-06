package me.alfredobejarano.bitsocodechallenge.repository

import me.alfredobejarano.bitsocodechallenge.datasource.remote.TradeChartApiService
import me.alfredobejarano.bitsocodechallenge.model.Mapper
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.model.remote.TradeChartItem
import javax.inject.Inject

/**
 * HistoryRepository
 */
class TradeChartRepository @Inject constructor(
    private val remoteDataSource: TradeChartApiService,
    private val mapper: Mapper<TradeChartItem, TradeChartPoint>
) {
    suspend fun getChart(book: String) =
        remoteDataSource.getTradeChart(book)?.map(mapper::map) ?: emptyList()
}