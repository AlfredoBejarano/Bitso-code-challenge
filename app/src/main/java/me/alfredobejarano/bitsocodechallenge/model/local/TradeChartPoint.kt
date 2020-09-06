package me.alfredobejarano.bitsocodechallenge.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Point
 */
@Parcelize
data class TradeChartPoint(val closePrice: Double, val date: Long) : Parcelable