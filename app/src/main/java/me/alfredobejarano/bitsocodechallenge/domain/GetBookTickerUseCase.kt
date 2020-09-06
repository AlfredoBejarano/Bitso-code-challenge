package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetBookTickerUseCase @Inject constructor(
    private val repository: BookRepository,
    private val fillBookTickerHistoryDataUseCase: FillBookTickerHistoryDataUseCase
) {

    /**
     * Retrieves the Ticker data of the given book.
     * @param book The book name to fetch it's ticker data. (Ex. btc_mxn).
     * @return Book object or null if said Boot wasn't found.
     */
    suspend fun getBookTicker(book: String) = repository.getBook(book)?.run {
        fillBookTickerHistoryDataUseCase.fillBookTickerData(this)
    }
}