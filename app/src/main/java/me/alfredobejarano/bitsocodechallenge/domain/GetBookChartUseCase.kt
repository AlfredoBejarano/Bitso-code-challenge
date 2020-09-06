package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.TradeChartRepository
import javax.inject.Inject

/**
 * GetBookChartUseCase
 */
class GetBookChartUseCase @Inject constructor(private val repository: TradeChartRepository) {
    suspend fun getChartPoints(book: String) = repository.getChart(book).apply {
        sortedBy { it.date }
    }
}