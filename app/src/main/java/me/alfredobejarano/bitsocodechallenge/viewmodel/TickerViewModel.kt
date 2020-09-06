package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.alfredobejarano.bitsocodechallenge.domain.GetBookTickerUseCase
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.launch
import me.alfredobejarano.bitsocodechallenge.utils.poll

/**
 * TickerViewModel
 */
class TickerViewModel @ViewModelInject constructor(
    private val getBookTickerUseCase: GetBookTickerUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    private var book = ""

    private val _bookLiveData = MutableLiveData<Book?>()
    val bookLiveData = _bookLiveData as LiveData<Book?>

    private val _booksLiveData = MutableLiveData<List<Book?>>()
    val booksLiveData = _booksLiveData as LiveData<List<Book?>>

    fun setup(bookName: String) {
        book = bookName
        getBooks()
        poll(::getBook)
        poll(::getBooks)
    }

    fun getBook() = launch(_bookLiveData) {
        getBookTickerUseCase.getBookTicker(book)
    }

    private fun getBooks() = launch(_booksLiveData) {
        getBooksUseCase.getBooks()
    }
}