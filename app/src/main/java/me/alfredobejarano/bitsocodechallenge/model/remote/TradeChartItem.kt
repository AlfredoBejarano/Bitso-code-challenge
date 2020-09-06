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
    val value: String? = "0.0",
    @SerializedName("volume")
    val volume: String? = "0.0",
    @SerializedName("open")
    val open: String? = "0.0",
    @SerializedName("low")
    val low: String? = "0.0",
    @SerializedName("high")
    val high: String? = "0.0",
    @SerializedName("close")
    val close: String? = "0.0",
    @SerializedName("vwap")
    val vwap: String? = "0.0"
)