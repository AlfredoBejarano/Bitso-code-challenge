package me.alfredobejarano.bitsocodechallenge.repository

import launchTest
import me.alfredobejarano.bitsocodechallenge.datasource.remote.TradeChartApiService
import me.alfredobejarano.bitsocodechallenge.model.Mapper
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.model.remote.TradeChartItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * TradeChartRepositoryTest
 */
@RunWith(MockitoJUnitRunner::class)
class TradeChartRepositoryTest {
    @Mock
    private lateinit var remoteMock: TradeChartApiService

    @Mock
    private lateinit var mockMapper: Mapper<TradeChartItem, TradeChartPoint>

    private lateinit var candidate: TradeChartRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        candidate = TradeChartRepository(remoteMock, mockMapper)
    }

    @Test
    fun `get book chart`() = launchTest {
        val testBook = "btc_mxn"

        val mockItem = TradeChartItem(close = 100.0, date = "2020-08-01")
        val mockResponse = listOf(mockItem)
        Mockito.`when`(remoteMock.getTradeChart(testBook)).thenReturn(mockResponse)

        candidate.getChart(testBook)

        Mockito.verify(remoteMock, Mockito.atLeastOnce()).getTradeChart(book = "btc_mxn")
        Mockito.verify(mockMapper, Mockito.atLeastOnce()).map(mockItem)
    }
}