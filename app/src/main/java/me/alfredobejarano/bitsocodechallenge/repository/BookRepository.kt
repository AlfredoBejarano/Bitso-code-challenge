package me.alfredobejarano.bitsocodechallenge.repository

import me.alfredobejarano.bitsocodechallenge.datasource.local.BookDao
import me.alfredobejarano.bitsocodechallenge.datasource.local.CacheManager
import me.alfredobejarano.bitsocodechallenge.datasource.remote.BitsoApiService
import me.alfredobejarano.bitsocodechallenge.model.Mapper
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val localDataSource: BookDao,
    private val cacheManager: CacheManager,
    private val mapper: Mapper<Ticker, Book>,
    private val remoteDataSource: BitsoApiService
) {
    /**
     * Retrieves the Ticker data of a Book.
     * @param book Book name to retrieve its data.
     */
    private suspend fun getBookTicker(book: String) =
        remoteDataSource.getBookTicker(book).payload

    /**
     * Retrieves the available books from Bitso api.
     * @return List of books from Bitso API.
     */
    private suspend fun getRemoteBooks() = remoteDataSource.getAvailableBooks().payload
        ?.map { getBookTicker(it.book.orEmpty()) }
        ?.requireNoNulls()
        ?.map(mapper::map)
        ?: emptyList()

    /**
     * Receives a list of books and stores them within the local database.
     * @param books List of books.
     * @return List of books that were cached.
     */
    private suspend fun cacheRemoteBooks(books: List<Book>) = books.apply {
        forEach { localDataSource.createOrUpdate(it) }
        cacheManager.createBookCache()
    }

    /**
     * Retrieves the list of books.
     * @return [List] of available books.
     */
    suspend fun getBooks() = if (cacheManager.isBookCacheValid()) {
        localDataSource.readAll()
    } else {
        cacheRemoteBooks(getRemoteBooks())
    } ?: emptyList()

    /**
     * Retrieves a book.
     * @param book Book name to retrieve.
     * @return [Book] object or null if said book can't be found.
     */
    suspend fun getBook(book: String) = if (cacheManager.isBookCacheValid()) {
        localDataSource.read(book)
    } else {
        getBookTicker(book)?.run(mapper::map)?.also { localDataSource.createOrUpdate(it) }
    }
}