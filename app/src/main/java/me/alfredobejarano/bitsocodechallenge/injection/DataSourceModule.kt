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
import me.alfredobejarano.bitsocodechallenge.datasource.local.OrderDao
import me.alfredobejarano.bitsocodechallenge.datasource.remote.AuthApiService
import me.alfredobejarano.bitsocodechallenge.datasource.remote.BitsoApiService
import me.alfredobejarano.bitsocodechallenge.datasource.remote.TradeChartApiService
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.local.BookOrder
import me.alfredobejarano.bitsocodechallenge.model.local.TradeChartPoint
import me.alfredobejarano.bitsocodechallenge.model.mapper.OrderIdToBookOrderMapper
import me.alfredobejarano.bitsocodechallenge.model.mapper.Mapper
import me.alfredobejarano.bitsocodechallenge.model.mapper.TickerToBookMapper
import me.alfredobejarano.bitsocodechallenge.model.mapper.TradeChartItemToChartPointTradeChartPoint
import me.alfredobejarano.bitsocodechallenge.model.remote.OrderId
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker
import me.alfredobejarano.bitsocodechallenge.model.remote.TradeChartItem
import me.alfredobejarano.bitsocodechallenge.utils.AuthenticationInterceptor
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

    private val authInterceptor by lazy { AuthenticationInterceptor() }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    private val okHttpAuthClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofitClient by lazy {
        Retrofit.Builder().addConverterFactory(gsonConverterFactory).client(okHttpClient)
    }

    private val authRetrofitClient by lazy {
        Retrofit.Builder().addConverterFactory(gsonConverterFactory).client(okHttpAuthClient)
    }

    @Provides
    fun provideTickerToBookMapper(): Mapper<Ticker, Book> = TickerToBookMapper()

    @Provides
    fun provideTradeChartItemToChartPointTradeChartPoint(): Mapper<TradeChartItem, TradeChartPoint> =
        TradeChartItemToChartPointTradeChartPoint()

    @Provides
    fun provideBookOrderResponseToBookOrderMapper(): Mapper<OrderId, BookOrder> =
        OrderIdToBookOrderMapper()

    @Provides
    @Singleton
    fun provideBitsoApiService(): BitsoApiService =
        retrofitClient.baseUrl(BuildConfig.BITSO_API_BASE_URL).build()
            .create(BitsoApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService =
        authRetrofitClient.baseUrl(BuildConfig.BITSO_API_BASE_URL).build()
            .create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideChartApiService(): TradeChartApiService =
        retrofitClient.baseUrl(BuildConfig.TRADE_CHART_API_BASE_URL).build()
            .create(TradeChartApiService::class.java)

    @Provides
    @Singleton
    fun provideBookDao(@ApplicationContext ctx: Context): BookDao =
        BookDatabase.getInstance(ctx).provideBookDao()

    @Provides
    @Singleton
    fun provideOrderDao(@ApplicationContext ctx: Context): OrderDao =
        BookDatabase.getInstance(ctx).provideOrderDao()

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext ctx: Context): CacheManager =
        CacheManager.getInstance(ctx)
}