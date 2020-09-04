package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

private const val POLL_UPDATE_TIME_SECONDS = 30L

fun <T> ViewModel.launch(liveData: MutableLiveData<T>, work: suspend () -> T) {
    EventManager.reportLoading(true)
    viewModelScope.launch(Dispatchers.IO) {
        try {
            liveData.postValue(work())
        } catch (e: Exception) {
            EventManager.reportError(e)
            liveData.postValue(null)
        }
    }
}

/**
 * Executes a given block every 30 seconds.
 */
fun ViewModel.poll(work: () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        delay(MILLISECONDS.convert(POLL_UPDATE_TIME_SECONDS, SECONDS))
        work()
        poll(work)
    }
}