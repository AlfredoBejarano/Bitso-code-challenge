package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object EventManager {
    private val mLoadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = mLoadingLiveData as LiveData<Boolean>

    fun reportLoading(isLoading: Boolean) = mLoadingLiveData.emit(isLoading)

    private val mErrorLiveData = MutableLiveData<Throwable>()
    val errorLiveData = mErrorLiveData as LiveData<Throwable>

    fun reportError(throwable: Throwable?) = mErrorLiveData.emit(throwable)
}