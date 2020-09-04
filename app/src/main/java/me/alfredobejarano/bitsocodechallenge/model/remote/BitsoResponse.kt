package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.SerializedName

/**
 * Data class that represent the scaffold from a Bitso API response.
 * @param T Generic that represents the result class contained in the payload property.
 */
data class BitsoResponse<T>(
    @SerializedName("success")
    var success: Boolean? = false,
    @SerializedName("payload")
    var payload: T? = null
)