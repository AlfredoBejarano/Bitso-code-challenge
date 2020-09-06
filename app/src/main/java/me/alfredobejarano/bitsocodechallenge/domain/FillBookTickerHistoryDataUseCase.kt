package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.model.local.Book
import javax.inject.Inject

/**
 * Fills a [Book] missing ticker information such as
 * its chart price history and last day growth percentage.
 */
class FillBookTickerHistoryDataUseCase @Inject constructor(
    private val getChartUseCase: GetChartUseCase,
    private val calculateGrowthUseCase: CalculateGrowthUseCase
) {
    /**
     * Receives a [Book] object and returns the updated item.
     * @param book Book item to fill it's missing data.
     * @return [Book] object with the data updated.
     */
    suspend fun fillBookTickerData(book: Book) = book.apply {
        chart = getChartUseCase.getChartPoints(this.book)
        growth = calculateGrowthUseCase.getBookGrowth(chart)
    }
}