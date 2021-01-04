package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Returns a [MutableLiveData] as an immutable [LiveData] for external observation
 */
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>