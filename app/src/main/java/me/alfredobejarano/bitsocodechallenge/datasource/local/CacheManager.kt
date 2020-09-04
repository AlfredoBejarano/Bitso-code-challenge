package me.alfredobejarano.bitsocodechallenge.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Date
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

class CacheManager private constructor(private val prefs: SharedPreferences) {

    fun createBookCache() = prefs.edit {
        val now = Date().time
        val cacheDuration = MILLISECONDS.convert(BOOK_CACHE_DURATIONS_SECONDS, SECONDS)
        putLong(BOOK_CACHE_TIMESTAMP_KEY, now + cacheDuration).apply()
    }

    fun isBookCacheValid(): Boolean {
        val now = Date().time
        val timeStamp = prefs.getLong(BOOK_CACHE_TIMESTAMP_KEY, now)
        return timeStamp > now
    }

    companion object {
        private const val BOOK_CACHE_DURATIONS_SECONDS = 30L
        private const val PREFERENCES_FILE_NAME = "cache_metadata"
        private const val BOOK_CACHE_TIMESTAMP_KEY = "books_cache"

        /**
         * Reference to the single Instance of the [CacheManager] class.
         */
        private var INSTANCE: CacheManager? = null

        /**
         * Creates an Instance of [CacheManager].
         */
        private fun createInstance(ctx: Context) = CacheManager(
            ctx.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        )

        /**
         * Retrieves the single instance of the [CacheManager] class.
         */
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createInstance(ctx).also { INSTANCE = it }
        }
    }
}