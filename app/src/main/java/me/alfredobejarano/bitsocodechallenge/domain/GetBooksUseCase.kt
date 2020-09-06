package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

/**
 * Use case class that retrieves the available books from the repository.
 */
class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository,
    private val fillBookTickerHistoryDataUseCase: FillBookTickerHistoryDataUseCase
) {
    /**
     * Retrieves the list of available books.
     * @return [List] of available Book items within Bitso.
     */
    suspend fun getBooks() = repository.getBooks().sortedWith(compareBy { it.book })
        .map { fillBookTickerHistoryDataUseCase.fillBookTickerData(it) }
}