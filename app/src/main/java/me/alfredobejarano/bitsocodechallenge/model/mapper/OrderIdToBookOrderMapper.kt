package me.alfredobejarano.bitsocodechallenge.model.mapper

import me.alfredobejarano.bitsocodechallenge.model.local.BookOrder
import me.alfredobejarano.bitsocodechallenge.model.local.OrderType
import me.alfredobejarano.bitsocodechallenge.model.remote.OrderId

class OrderIdToBookOrderMapper : Mapper<OrderId, BookOrder> {
    /**
     * **Stub**
     */
    override fun map(item: OrderId) = BookOrder(orderId = item.orderId.orEmpty())
}