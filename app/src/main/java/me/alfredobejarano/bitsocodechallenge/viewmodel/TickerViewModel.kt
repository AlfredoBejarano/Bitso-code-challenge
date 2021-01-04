package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.alfredobejarano.bitsocodechallenge.domain.GetBookTickerUseCase
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.executeWork
import me.alfredobejarano.bitsocodechallenge.utils.pollWork

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
        // getBooks()
        // pollWork(::getBook)
        // pollWork(::getBooks)
    }

    /**fun getBook() = executeWork(_bookLiveData) {
        getBookTickerUseCase.getBookTicker(book)
    }

    private fun getBooks() = executeWork(_booksLiveData) {
        getBooksUseCase.getBooks()
    }**/
}