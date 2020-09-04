package me.alfredobejarano.bitsocodechallenge.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.alfredobejarano.bitsocodechallenge.model.local.Book

/**
 * Dao object that gives access to the Book local cache tables.
 */
@Dao
interface BookDao {
    /**
     * Creates a Book record in the local cache.
     * If the record already exists, the book data will be updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate()

    /**
     * Retrieves the details of a Book from the local cache.
     * @return [Book] - Details of the book from the local cache.
     */
    @Query("SELECT * FROM Books WHERE pk == :book LIMIT 1")
    suspend fun read(book: String): Book?

    /**
     * Retrieves all of the available books from the local cache.
     * @return - [List] of [Book] objects stored in cache.
     */
    @Query("SELECT * FROM Books ORDER BY pk")
    suspend fun readAll(): List<Book>?
}