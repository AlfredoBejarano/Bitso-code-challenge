package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository,
    private val getBookChartUseCase: GetBookChartUseCase,
    private val getBookGrowthUseCase: GetBookGrowthUseCase
) {
    suspend fun getBooks() = repository.getBooks().sortedWith(compareBy { it.book }).apply {
        forEach {
            it.chart = getBookChartUseCase.getChartPoints(it.book)
            it.growth = getBookGrowthUseCase.getBookGrowth(it.chart)
        }
    }
}