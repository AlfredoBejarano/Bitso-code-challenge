package me.alfredobejarano.bitsocodechallenge.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.alfredobejarano.bitsocodechallenge.BuildConfig
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.local.BookOrder

@TypeConverters(OrderTypeConverter::class)
@Database(entities = [Book::class, BookOrder::class], version = BuildConfig.VERSION_CODE, exportSchema = true)
abstract class BookDatabase : RoomDatabase() {
    abstract fun provideBookDao(): BookDao
    abstract fun provideOrderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        /**
         * Create an Instance of the [BookDatabase] class.
         */
        private fun createInstance(ctx: Context) = Room.databaseBuilder(
            ctx,
            BookDatabase::class.java,
            "${BuildConfig.APPLICATION_ID}.database"
        ).fallbackToDestructiveMigration().build()

        /**
         * Retrieves the instance of [BookDatabase].
         */
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createInstance(ctx).also { INSTANCE = it }
        }
    }
}