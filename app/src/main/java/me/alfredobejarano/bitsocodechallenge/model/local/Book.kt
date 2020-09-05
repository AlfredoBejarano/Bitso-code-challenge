package me.alfredobejarano.bitsocodechallenge.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Books")
data class Book(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = false)
    val book: String = "",
    val volume: Double = 0.0,
    val dayLow: Double = 0.0,
    val dayHigh: Double = 0.0,
    val bidPrice: Double = 0.0,
    val askPrice: Double = 0.0,
    val lastPrice: Double = 0.0
) : Parcelable {
    fun areContentsTheSame(other: Book) = book == other.book && volume == other.volume &&
            dayLow == other.dayLow && dayHigh == other.dayHigh && bidPrice == other.bidPrice &&
            askPrice == other.askPrice && lastPrice == other.lastPrice
}