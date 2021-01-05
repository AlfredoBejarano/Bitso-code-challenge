package me.alfredobejarano.bitsocodechallenge.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderId(
    @Expose
    @SerializedName("oid")
    val orderId: String? = ""
)