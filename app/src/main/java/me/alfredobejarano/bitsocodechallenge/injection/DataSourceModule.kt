package me.alfredobejarano.bitsocodechallenge.injection

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
class DataSourceModule(private val application: Application) {
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
            .baseUrl("TODO - Bitso API")
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
    fun provideCacheManager(): CacheManager = CacheManager.getInstance(application)

    @Provides
    @Singleton
    fun provideBookDao(): BookDao = BookDatabase.getInstance(application).provideBookDao()
}