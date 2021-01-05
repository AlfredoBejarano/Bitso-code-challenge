package me.alfredobejarano.bitsocodechallenge.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.alfredobejarano.bitsocodechallenge.model.local.BookOrder

@Dao
interface OrderDao {
    /**
     * Creates an Order record in the local cache.
     * If the record already exists, the book data will be updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(order: BookOrder)

    /**
     * Retrieves orders placed from a given book.
     * @return [BookOrder] - Orders for the given book.
     */
    @Query("SELECT * FROM Orders WHERE book = :book")
    suspend fun readByBook(book: String): List<BookOrder>?
}