package me.alfredobejarano.bitsocodechallenge.repository

import launchTest
import me.alfredobejarano.bitsocodechallenge.datasource.local.BookDao
import me.alfredobejarano.bitsocodechallenge.datasource.local.CacheManager
import me.alfredobejarano.bitsocodechallenge.datasource.remote.BitsoApiService
import me.alfredobejarano.bitsocodechallenge.model.Mapper
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.remote.AvailableBook
import me.alfredobejarano.bitsocodechallenge.model.remote.BitsoResponse
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * BookRepositoryTest
 */
@RunWith(MockitoJUnitRunner::class)
class BookRepositoryTest {
    @Mock
    private lateinit var localDataSource: BookDao

    @Mock
    private lateinit var cacheDataSource: CacheManager

    @Mock
    private lateinit var remoteDataSource: BitsoApiService

    @Mock
    private lateinit var mapper: Mapper<Ticker, Book>

    private lateinit var testCandidate: BookRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testCandidate = BookRepository(localDataSource, cacheDataSource, mapper, remoteDataSource)
    }

    @Test
    fun `get books when cache is valid`() = launchTest {
        val mockList = emptyList<Book>()
        Mockito.`when`(cacheDataSource.isBookCacheValid()).thenReturn(true)

        val result = testCandidate.getBooks()
        assert(result == mockList)

        Mockito.verify(localDataSource, Mockito.only()).readAll()
        Mockito.verify(cacheDataSource, Mockito.only()).isBookCacheValid()
        Mockito.verify(remoteDataSource, Mockito.never()).getAvailableBooks()
    }

    @Test
    fun `get books when cache is invalid`() = launchTest {
        val mockResult = Book(book = "btc_mxn")
        val ticker = Ticker(book = "btc_mxn")
        val mockList = BitsoResponse(payload = listOf(AvailableBook(book = "btc_mxn")))

        Mockito.`when`(mapper.map(ticker)).thenReturn(mockResult)
        Mockito.`when`(remoteDataSource.getAvailableBooks()).thenReturn(mockList)
        Mockito.`when`(cacheDataSource.isBookCacheValid()).thenReturn(false)
        Mockito.`when`(remoteDataSource.getBookTicker("btc_mxn"))
            .thenReturn(BitsoResponse(payload = ticker))

        val result = testCandidate.getBooks()
        assert(result.size == mockList.payload?.size)

        Mockito.verify(localDataSource, Mockito.never()).readAll()
        Mockito.verify(cacheDataSource, Mockito.atLeastOnce()).isBookCacheValid()
        Mockito.verify(remoteDataSource, Mockito.times(2)).getAvailableBooks()
    }

    @Test
    fun `get book when cache is valid`() = launchTest {
        val mockBook = Book(book = "btc_mxn")

        Mockito.`when`(localDataSource.read("btc_mxn")).thenReturn(mockBook)
        Mockito.`when`(cacheDataSource.isBookCacheValid()).thenReturn(true)

        val result = testCandidate.getBook("btc_mxn")

        assert(result is Book)
        assert(result?.book == mockBook.book)

        Mockito.verify(localDataSource, Mockito.only()).read("btc_mxn")
        Mockito.verify(cacheDataSource, Mockito.only()).isBookCacheValid()
        Mockito.verify(remoteDataSource, Mockito.never()).getBookTicker("btc_mxn")
    }

    @Test
    fun `get book when cache is invalid`() = launchTest {
        val mockTickerPayload = Ticker(book = "btc_mxn")
        val mockTicker = BitsoResponse(payload = mockTickerPayload)

        Mockito.`when`(mapper.map(mockTickerPayload)).thenReturn(Book(book = "btc_mxn"))
        Mockito.`when`(remoteDataSource.getBookTicker("btc_mxn")).thenReturn(mockTicker)
        Mockito.`when`(cacheDataSource.isBookCacheValid()).thenReturn(false)

        val result = testCandidate.getBook("btc_mxn")

        assert(result is Book)
        assert(result?.book == mockTickerPayload.book)

        Mockito.verify(cacheDataSource, Mockito.only()).isBookCacheValid()
        Mockito.verify(remoteDataSource, Mockito.only()).getBookTicker("btc_mxn")
    }
}