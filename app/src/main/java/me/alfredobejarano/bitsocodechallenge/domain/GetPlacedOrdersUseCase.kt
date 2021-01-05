package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetPlacedOrdersUseCase @Inject constructor(private val repository: BookRepository) {

    /**
     * Retrieves the list of orders by the given book id.
     * @param bookId - Id of the book to retrieve placed orders.
     */
    suspend fun getPlacedOrdersForBook(bookId: String) = repository.getPlacedOrders(bookId)
}