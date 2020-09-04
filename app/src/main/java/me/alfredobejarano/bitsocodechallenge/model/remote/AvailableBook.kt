package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a book within the Bitso API.
 */
data class AvailableBook(
    @SerializedName("book")
    var book: String? = "",
    @SerializedName("minimum_amount")
    var minimumAmount: Double? = 0.0,
    @SerializedName("maximum_amount")
    var maximumAmount: Double? = 0.0,
    @SerializedName("minimum_price")
    var minimumPrice: Double? = 0.0,
    @SerializedName("maximum_price")
    var maximumPrice: Double? = 0.0,
    @SerializedName("minimum_value")
    var minimumValue: Double? = 0.0,
    @SerializedName("maximum_value")
    var maximumValue: Double? = 0.0
)