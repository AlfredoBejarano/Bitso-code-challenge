package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("volume")
    var volume: Double? = 0.0,
    @SerializedName("high")
    var high: Double? = 0.0,
    @SerializedName("last")
    var last: Double? = 0.0,
    @SerializedName("low")
    var low: Double? = 0.0,
    @SerializedName("book")
    var book: String? = "",
    @SerializedName("vwap")
    var vwap: Double? = 0.0,
    @SerializedName("ask")
    var ask: Double? = 0.0,
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("bid")
    var bid: String? = ""
)