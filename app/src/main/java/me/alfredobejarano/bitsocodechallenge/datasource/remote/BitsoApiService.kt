package me.alfredobejarano.bitsocodechallenge.datasource.remote

import me.alfredobejarano.bitsocodechallenge.model.remote.AvailableBook
import me.alfredobejarano.bitsocodechallenge.model.remote.BitsoResponse
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker
import retrofit2.http.GET
import retrofit2.http.Query

interface BitsoApiService {
    /**
     * Fetches a list of available books within Bitso.
     * @return [BitsoResponse] object containing a [List] of [AvailableBook] objects as its payload.
     */
    @GET("available_books/")
    suspend fun getAvailableBooks(): BitsoResponse<List<AvailableBook>>

    /**
     * Retrieves the ticker details of a book.
     * @param book Book name to retrieve it's ticker (ex. btc_mxn).
     */
    @GET("ticker/")
    suspend fun getBookTicker(@Query("book") book: String): BitsoResponse<Ticker>
}