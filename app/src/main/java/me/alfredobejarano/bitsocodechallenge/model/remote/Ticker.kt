package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("volume")
    var volume: String? = "0.0",
    @SerializedName("high")
    var high: String? = "0.0",
    @SerializedName("last")
    var last: String? = "0.0",
    @SerializedName("low")
    var low: String? = "0.0",
    @SerializedName("book")
    var book: String? = "",
    @SerializedName("vwap")
    var vwap: String? = "0.0",
    @SerializedName("ask")
    var ask: String? = "0.0",
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("bid")
    var bid: String? = "0.0"
)