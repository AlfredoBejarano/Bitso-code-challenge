package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object EventManager {
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = _loadingLiveData as LiveData<Boolean>

    fun reportLoading(isLoading: Boolean) = _loadingLiveData.postValue(isLoading)
}