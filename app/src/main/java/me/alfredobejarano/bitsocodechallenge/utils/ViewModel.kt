package me.alfredobejarano.bitsocodechallenge.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit.MILLISECONDS
import me.alfredobejarano.bitsocodechallenge.model.Result
import java.util.concurrent.TimeUnit.SECONDS

private const val POLL_UPDATE_TIME_SECONDS = 30L

fun <T : Any> ViewModel.executeWork(liveData: MutableLiveData<Result<T>>, work: suspend () -> T) =
    viewModelScope.launch(Dispatchers.IO) {
        liveData.postValue(Result.Progress)
        try {
            val workResult = work()
            liveData.postValue(Result.Success(workResult))
        } catch (e: Exception) {
            liveData.postValue(Result.Error(e))
        }
    }

/**
 * Executes a given block every 30 seconds.
 */
fun ViewModel.pollWork(work: () -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        delay(MILLISECONDS.convert(POLL_UPDATE_TIME_SECONDS, SECONDS))
        work()
        pollWork(work)
    }
}