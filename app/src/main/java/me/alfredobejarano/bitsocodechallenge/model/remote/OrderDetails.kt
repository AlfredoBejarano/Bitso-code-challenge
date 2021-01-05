package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Body to place a limit order to buy/sell a book.
 */
data class OrderDetails(
    @Expose
    @SerializedName("book")
    val bookId: String = "",
    @Expose
    @SerializedName("side")
    val side: String = "",
    @Expose
    @SerializedName("price")
    val price: String = "0.0",
    @Expose
    @SerializedName("major")
    val major: String = "0.0",
    @Expose
    @SerializedName("type")
    val type: String = "limit",
    @Expose
    @SerializedName("time_in_force")
    val timeInForce: String = "goodtillcancelled"
)