package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.model.local.BookOrder
import me.alfredobejarano.bitsocodechallenge.model.local.OrderType
import me.alfredobejarano.bitsocodechallenge.model.remote.OrderDetails
import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import java.util.Locale
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val repository: BookRepository) {

    /**
     * Places a limit order for the given book.
     *
     * @param bookId - Book id to place the order.
     * @param type - If the order is for sell or buy.
     */
    suspend fun placeLimitOrder(bookId: String, type: OrderType): BookOrder {
        val orderType = type.toString().toLowerCase(Locale.getDefault())
        return repository.placeBookLimitOrder(OrderDetails(bookId, orderType)).apply {
            this.type = type
            this.book = bookId
        }
    }
}