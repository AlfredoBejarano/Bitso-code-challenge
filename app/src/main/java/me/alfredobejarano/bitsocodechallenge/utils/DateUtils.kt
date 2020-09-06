package me.alfredobejarano.bitsocodechallenge.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * DateUtils
 */

fun String?.asDate(pattern: String) = try {
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this.orEmpty()) ?: Date()
} catch (e: Exception) {
    Date()
}