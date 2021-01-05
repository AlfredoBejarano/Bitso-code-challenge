package me.alfredobejarano.bitsocodechallenge.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Orders")
data class BookOrder(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = false)
    var orderId: String = "",
    var book: String = "",
    var type: OrderType = OrderType.BUY,
)