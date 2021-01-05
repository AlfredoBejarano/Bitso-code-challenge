package me.alfredobejarano.bitsocodechallenge.datasource.local

import androidx.room.TypeConverter
import me.alfredobejarano.bitsocodechallenge.model.local.OrderType
import java.math.BigDecimal
import java.util.Locale

class OrderTypeConverter {
    @TypeConverter
    fun fromStringToOrderType(value: String) = OrderType.valueOf(value)

    @TypeConverter
    fun fromOrderTypeToString(value: OrderType) = value.toString().toLowerCase(Locale.getDefault())

    @TypeConverter
    fun fromStringToBigDecimal(value: String) = value.toBigDecimal()

    @TypeConverter
    fun fromBigDecimalToString(value: BigDecimal) = value.toPlainString()
}