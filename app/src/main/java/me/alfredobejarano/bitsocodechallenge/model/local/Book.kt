package me.alfredobejarano.bitsocodechallenge.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Books")
data class Book(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = false)
    var book: String = "",
    var volume: Double = 0.0,
    var dayLow: Double = 0.0,
    var dayHigh: Double = 0.0,
    var bidPrice: Double = 0.0,
    var askPrice: Double = 0.0,
    var lastPrice: Double = 0.0,
    @Ignore
    var growth: Double = 0.0,
    @Ignore
    var chart: List<TradeChartPoint> = emptyList()
) : Parcelable {
    fun areContentsTheSame(other: Book) = book == other.book && volume == other.volume &&
            dayLow == other.dayLow && dayHigh == other.dayHigh && bidPrice == other.bidPrice &&
            askPrice == other.askPrice && lastPrice == other.lastPrice
}