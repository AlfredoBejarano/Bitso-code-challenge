package me.alfredobejarano.bitsocodechallenge.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlinx.coroutines.Job
import me.alfredobejarano.bitsocodechallenge.R
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint

/**
 * GraphView
 */
class GraphView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var accentColor = 0
    private var hasDrawn = false
    private var fetchingJob: Job? = null
    private var data: List<TradeChartPoint> = emptyList()

    private val linePaint by lazy {
        Paint().apply {
            getAccentColor()
            strokeWidth = 4f
            color = accentColor
        }
    }

    fun setData(tradeChartPoints: List<TradeChartPoint>) {
        data = tradeChartPoints
        hasDrawn = false
        invalidate()
    }

    private fun drawGraph(canvas: Canvas) {
        if (data.isEmpty() || width == 0) return
        if (!hasDrawn) {
            val xSpan = (width / (data.size - 1)).toFloat()
            for (i in data.indices) {
                if (i < (data.size - 1)) {
                    val startX = i * xSpan
                    val startY = getLastPriceAsPixels(data[i].closePrice)
                    val endX = (i + 1) * xSpan
                    val endY = getLastPriceAsPixels(data[i + 1].closePrice)

                    canvas.drawLine(startX, startY, endX, endY, linePaint)
                }
            }
            hasDrawn = true
        }
    }

    private fun getLastPriceAsPixels(lastPrice: Double): Float {
        val maxPrice = data.maxByOrNull { it.closePrice }?.closePrice ?: height.toDouble()
        return height - ((lastPrice * height) / maxPrice).toFloat()
    }

    private fun getAccentColor() =
        context.obtainStyledAttributes(TypedValue().data, intArrayOf(R.attr.colorAccent)).apply {
            accentColor = getColor(0, 0)
            recycle()
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run(::drawGraph)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (fetchingJob?.isActive == true) {
            fetchingJob?.cancel()
        }
        fetchingJob = null
    }
}