package me.alfredobejarano.bitsocodechallenge.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import me.alfredobejarano.bitsocodechallenge.R
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.widgets.GraphView
import java.text.DecimalFormat

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("growth")
        fun setGrowth(view: TextView, growth: Double) {
            val grow = growth > 0
            val decimalText = DecimalFormat("##.##").format(growth)

            val drawable = if (grow) {
                R.drawable.ic_baseline_arrow_drop_up_24
            } else {
                R.drawable.ic_baseline_arrow_drop_down_24
            }

            view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, 0, 0, 0)

            view.setTextColor(
                ContextCompat.getColor(
                    view.context, if (grow) {
                        R.color.colorGreen
                    } else {
                        R.color.colorRed
                    }
                )
            )

            view.text = if (grow) {
                "+$decimalText%"
            } else {
                "$decimalText%"
            }
        }

        @JvmStatic
        @BindingAdapter("chart_points")
        fun setChartPoints(view: GraphView, points: List<TradeChartPoint>) = view.setData(points)
    }
}