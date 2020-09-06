package me.alfredobejarano.bitsocodechallenge.datasource.remote

import me.alfredobejarano.bitsocodechallenge.model.remote.TradeChartItem
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * TradeChartApiService
 */
interface TradeChartApiService {
    @GET("{book}/{time}")
    suspend fun getTradeChart(
        @Path("book") book: String,
        @Path("time") time: String = "1month"
    ): List<TradeChartItem>?
}