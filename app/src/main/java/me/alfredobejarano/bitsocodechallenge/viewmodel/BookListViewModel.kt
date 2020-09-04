package me.alfredobejarano.bitsocodechallenge.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.EventManager
import java.util.concurrent.TimeUnit

class BookListViewModel @ViewModelInject constructor(private val getBooksUseCase: GetBooksUseCase) :
    ViewModel() {

    private val _booksLiveData = MutableLiveData<List<Book>>()
    val booksLiveData = _booksLiveData as LiveData<List<Book>>

    /**
     * Starts the polling task, it will update the book list every 30 seconds.
     */
    private fun startPollingJob() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS))
            getBooks()
        }
    }

    /**
     * Retrieves the list of books.
     */
    fun getBooks() {
        EventManager.reportLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            _booksLiveData.postValue(getBooksUseCase.getBooks())
            startPollingJob()
        }
    }
}