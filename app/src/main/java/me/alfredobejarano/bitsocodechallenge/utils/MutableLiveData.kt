package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.MutableLiveData

/**
 * MutableLiveData
 */

fun <T> MutableLiveData<T>?.emit(newValue: T?) = try {
    this?.value = newValue
} catch (e: Exception) {
    this?.postValue(newValue)
}