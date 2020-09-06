package me.alfredobejarano.bitsocodechallenge.domain

import launchTest
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * GetBookTickerUseCase
 */
@RunWith(MockitoJUnitRunner::class)
class GetBookTickerUseCaseTest {
    @Mock
    private lateinit var mockRepository: BookRepository

    @Mock
    private lateinit var fillBookTickerHistoryDataUseCase: FillBookTickerHistoryDataUseCase

    private lateinit var testSubject: GetBookTickerUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testSubject = GetBookTickerUseCase(mockRepository, fillBookTickerHistoryDataUseCase)
    }

    @Test
    fun getBookTickerTest() = launchTest {
        Mockito.`when`(mockRepository.getBook("btc_usd")).thenReturn(Book())
        Mockito.`when`(fillBookTickerHistoryDataUseCase.fillBookTickerData(Book()))
            .thenReturn(Book(growth = 0.0))

        val result = testSubject.getBookTicker("btc_usd")

        assert(result is Book)
        assert(result?.growth == 0.0)
    }

    @Test
    fun getBookTickerTest_thenNullTest() = launchTest {
        Mockito.`when`(mockRepository.getBook("btc_usd")).thenReturn(null)

        val result = testSubject.getBookTicker("btc_usd")

        assert(result == null)
        Mockito.verify(fillBookTickerHistoryDataUseCase, Mockito.never()).fillBookTickerData(Book())
    }
}