package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetBookUseCase @Inject constructor(
    private val repository: BookRepository, private val getBookChartUseCase: GetBookChartUseCase,
    private val getBookGrowthUseCase: GetBookGrowthUseCase) {
    suspend fun getBook(book: String) = repository.getBook(book)?.apply {
        chart = getBookChartUseCase.getChartPoints(book)
        growth = getBookGrowthUseCase.getBookGrowth(chart)
    }
}