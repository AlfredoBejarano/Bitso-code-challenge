package me.alfredobejarano.bitsocodechallenge.injection

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.alfredobejarano.bitsocodechallenge.BuildConfig
import me.alfredobejarano.bitsocodechallenge.datasource.local.BookDao
import me.alfredobejarano.bitsocodechallenge.datasource.local.BookDatabase
import me.alfredobejarano.bitsocodechallenge.datasource.local.CacheManager
import me.alfredobejarano.bitsocodechallenge.datasource.remote.BitsoApiService
import me.alfredobejarano.bitsocodechallenge.model.TickerToBookMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module class that tells Hilt how to provide the app various data sources.
 */
@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {
    private val gsonConverterFactory by lazy { GsonConverterFactory.create(Gson()) }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BITSO_API_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideTickerToBookMapper() = TickerToBookMapper()

    @Provides
    @Singleton
    fun provideBitsoApiService(): BitsoApiService =
        retrofitClient.create(BitsoApiService::class.java)

    @Provides
    @Singleton
    fun provideBookDao(@ApplicationContext ctx: Context): BookDao =
        BookDatabase.getInstance(ctx).provideBookDao()

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext ctx: Context): CacheManager =
        CacheManager.getInstance(ctx)
}