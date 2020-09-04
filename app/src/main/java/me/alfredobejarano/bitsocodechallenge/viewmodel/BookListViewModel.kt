package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.launch
import me.alfredobejarano.bitsocodechallenge.utils.poll

class BookListViewModel @ViewModelInject constructor(private val getBooksUseCase: GetBooksUseCase) :
    ViewModel() {

    private val _booksLiveData = MutableLiveData<List<Book>>()
    val booksLiveData = _booksLiveData as LiveData<List<Book>>

    init {
        poll(::getBooks)
    }

    /**
     * Retrieves the list of books.
     */
    fun getBooks() = launch(_booksLiveData) {
        getBooksUseCase.getBooks()
    }
}