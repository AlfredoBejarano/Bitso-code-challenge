package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.SerializedName

/**
 * TradeChartItem
 */
data class TradeChartItem(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("dated")
    val dated: String? = "",
    @SerializedName("value")
    val value: Double? = 0.0,
    @SerializedName("volume")
    val volume: Double? = 0.0,
    @SerializedName("open")
    val open: Double? = 0.0,
    @SerializedName("low")
    val low: Double? = 0.0,
    @SerializedName("high")
    val high: Double? = 0.0,
    @SerializedName("close")
    val close: Double? = 0.0,
    @SerializedName("vwap")
    val vwap: Double? = 0.0
)