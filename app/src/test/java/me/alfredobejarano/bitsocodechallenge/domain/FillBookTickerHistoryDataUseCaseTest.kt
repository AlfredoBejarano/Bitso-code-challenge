package me.alfredobejarano.bitsocodechallenge.domain

import launchTest
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FillBookTickerHistoryDataUseCaseTest {
    @Mock
    private lateinit var getChartUseCase: GetChartUseCase
    private lateinit var testCandidate: FillBookTickerHistoryDataUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testCandidate = FillBookTickerHistoryDataUseCase(getChartUseCase, CalculateGrowthUseCase())
    }

    @Test
    fun fillBookTickerDataTest() = launchTest {
        val book = Book(book = "mana_mxn")
        val mockChart = listOf<TradeChartPoint>()
        Mockito.`when`(getChartUseCase.getChartPoints("mana_mxn")).thenReturn(mockChart)

        val newBook = testCandidate.fillBookTickerData(book)

        assert(newBook.chart == mockChart)
        assert(newBook.growth == 0.0)

        Mockito.verify(getChartUseCase, Mockito.only()).getChartPoints("mana_mxn")
    }
}