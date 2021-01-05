package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.Result
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.asLiveData
import me.alfredobejarano.bitsocodechallenge.utils.cancelSafely
import me.alfredobejarano.bitsocodechallenge.utils.executeWork
import me.alfredobejarano.bitsocodechallenge.utils.pollWork

class BookListViewModel @ViewModelInject constructor(private val getBooksUseCase: GetBooksUseCase) :
    ViewModel() {

    private val bookPollingJob = pollWork(::getBooks)

    private val _booksLiveData = MutableLiveData<Result<List<Book>>>()
    val booksLiveData = _booksLiveData.asLiveData()

    /**
     * Retrieves the list of books.
     */
    fun getBooks() = executeWork(_booksLiveData) {
        getBooksUseCase.getBooks()
    }

    override fun onCleared() {
        super.onCleared()
        bookPollingJob.cancelSafely()
    }
}