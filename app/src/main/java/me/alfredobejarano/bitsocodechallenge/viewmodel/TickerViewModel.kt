package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.alfredobejarano.bitsocodechallenge.domain.GetBookUseCase
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.launch
import me.alfredobejarano.bitsocodechallenge.utils.poll

/**
 * TickerViewModel
 */
class TickerViewModel @ViewModelInject constructor(private val getBookUseCase: GetBookUseCase) :
    ViewModel() {
    private var book = ""

    private val _bookLiveData = MutableLiveData<Book?>()
    val bookLiveData = _bookLiveData as LiveData<Book?>

    fun setup(bookName: String) {
        book = bookName
        poll(::getBook)
    }

    fun getBook() = launch(_bookLiveData) {
        getBookUseCase.getBook(book)
    }
}