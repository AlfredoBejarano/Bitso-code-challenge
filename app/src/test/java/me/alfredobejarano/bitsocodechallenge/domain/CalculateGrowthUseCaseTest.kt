package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

/**
 * CalculateGrowthUseCaseTest
 */
@RunWith(JUnit4::class)
class CalculateGrowthUseCaseTest {
    @Test
    fun `calculate 24 hours growth`() {
        val mockPoints = listOf(
            TradeChartPoint(100.00, Date().time),
            TradeChartPoint(108.00, Date().time + 1)
        )
        assert(CalculateGrowthUseCase().getBookGrowth(mockPoints) == 8.0)
    }

    @Test
    fun `calculate 24 hours growth in incomplete chart`() {
        val mockPoints = listOf(TradeChartPoint(100.00, Date().time))
        assert(CalculateGrowthUseCase().getBookGrowth(mockPoints) == 0.0)
    }
}