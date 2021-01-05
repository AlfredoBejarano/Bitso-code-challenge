package me.alfredobejarano.bitsocodechallenge.model.mapper

import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker

class TickerToBookMapper : Mapper<Ticker, Book> {

    /**
     * **Stub**
     */
    override fun map(item: Ticker) = item.run {
        Book(
            book = book.orEmpty(),
            volume = volume?.toDouble() ?: 0.0,
            dayLow = low?.toDouble() ?: 0.0,
            dayHigh = high?.toDouble() ?: 0.0,
            bidPrice = bid?.toDouble() ?: 0.0,
            askPrice = ask?.toDouble() ?: 0.0,
            lastPrice = last?.toDouble() ?: 0.0
        )
    }
}