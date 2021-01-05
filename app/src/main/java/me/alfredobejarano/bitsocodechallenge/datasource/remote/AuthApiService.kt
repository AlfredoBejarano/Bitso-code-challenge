package me.alfredobejarano.bitsocodechallenge.datasource.remote

import me.alfredobejarano.bitsocodechallenge.model.remote.BitsoResponse
import me.alfredobejarano.bitsocodechallenge.model.remote.OrderDetails
import me.alfredobejarano.bitsocodechallenge.model.remote.OrderId
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    /**
     * Place an order to sell or buy a given [Book].
     * @param orderDetails - Details such as book or side (buy/sell) to place an order.
     */
    @POST("orders/")
    suspend fun placeAnOrder(@Body orderDetails: OrderDetails): BitsoResponse<OrderId>
}