package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.TradeChartRepository
import javax.inject.Inject

/**
 * GetBookChartUseCase
 */
class GetChartUseCase @Inject constructor(private val repository: TradeChartRepository) {
    /**
     * Retrieves the last month price history for the given book.
     * @param book Book name (ex. btc_mxn).
     * @return [List] of price points from the book's last month.
     */
    suspend fun getChartPoints(book: String) = repository.getChart(book).apply {
        sortedBy { it.date }
    }
}