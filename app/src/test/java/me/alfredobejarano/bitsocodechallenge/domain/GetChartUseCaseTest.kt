package me.alfredobejarano.bitsocodechallenge.domain

import launchTest
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.repository.TradeChartRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * GetChartUseCaseTest
 */
@RunWith(MockitoJUnitRunner::class)
class GetChartUseCaseTest {
    @Mock
    private lateinit var mockRepository: TradeChartRepository

    private lateinit var testCandidate: GetChartUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testCandidate = GetChartUseCase(mockRepository)
    }

    @Test
    fun getCharPointsTest() = launchTest {
        val mockList = listOf(
            TradeChartPoint(0.0, 31),
            TradeChartPoint(0.0, 4),
            TradeChartPoint(0.0, 1),
            TradeChartPoint(0.0, 12)
        )

        val sortedMockList = listOf(
            TradeChartPoint(0.0, 1),
            TradeChartPoint(0.0, 4),
            TradeChartPoint(0.0, 12),
            TradeChartPoint(0.0, 31)
        )

        Mockito.`when`(mockRepository.getChart("btc_eth"))
            .thenReturn(mockList)

        val result = testCandidate.getChartPoints("btc_eth")
        for (i in result.indices) {
            assert(result[i].date == sortedMockList[i].date)
        }
    }
}