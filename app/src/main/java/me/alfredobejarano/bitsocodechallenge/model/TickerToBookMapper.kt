package me.alfredobejarano.bitsocodechallenge.model

import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.model.remote.Ticker

class TickerToBookMapper : Mapper<Ticker, Book> {
    override fun map(item: Ticker) = item.run {
        Book(
            book = book.orEmpty(),
            volume = volume ?: 0.0,
            dayLow = low ?: 0.0,
            dayHigh = high ?: 0.0,
            bidPrice = bid ?: 0.0,
            askPrice = ask ?: 0.0,
            lastPrice = last ?: 0.0
        )
    }
}