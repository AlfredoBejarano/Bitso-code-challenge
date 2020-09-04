package me.alfredobejarano.bitsocodechallenge.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class Book(
    @PrimaryKey(autoGenerate = false)
    val book: String = "",
    val volume: Double = 0.0,
    val dayLow: Double = 0.0,
    val dayHigh: Double = 0.0,
    val bidPrice: Double = 0.0,
    val askPrice: Double = 0.0,
    val lastPrice: Double = 0.0
)