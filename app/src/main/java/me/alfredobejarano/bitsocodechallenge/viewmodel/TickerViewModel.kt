package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import me.alfredobejarano.bitsocodechallenge.domain.GetBookTickerUseCase
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase
import me.alfredobejarano.bitsocodechallenge.model.Result
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.utils.asLiveData
import me.alfredobejarano.bitsocodechallenge.utils.cancelSafely
import me.alfredobejarano.bitsocodechallenge.utils.executeWork
import me.alfredobejarano.bitsocodechallenge.utils.pollWork

/**
 * TickerViewModel
 */
class TickerViewModel @ViewModelInject constructor(
    private val getBookTickerUseCase: GetBookTickerUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {
    private var bookName = ""

    private var bookListPollJob: Job? = null
    private var bookTickerPollJob: Job? = null

    private val _tickerLiveData = MutableLiveData<Result<Book>>()
    val tickerLiveData = _tickerLiveData.asLiveData()

    private val _booksLiveData = MutableLiveData<Result<List<Book>>>()
    val booksLiveData = _booksLiveData.asLiveData()

    /**
     * Assigns the currently viewed book name to this [ViewModel] instance.
     */
    fun setup(bookName: String) {
        this.bookName = bookName
        getBooks()

        bookListPollJob = pollWork(::getBooks)
        bookTickerPollJob = pollWork(::getBookTicker)
    }

    /**
     * Finishes any current poll jobs when the ViewModel is being cleared from its LifeCycleOwner.
     */
    override fun onCleared() {
        super.onCleared()

        bookListPollJob.cancelSafely()
        bookListPollJob = null

        bookTickerPollJob.cancelSafely()
        bookTickerPollJob = null
    }

    /**
     * Retrieves the ticker details of the current [Book].
     */
    fun getBookTicker() = executeWork(_tickerLiveData) {
        getBookTickerUseCase.getBookTicker(bookName) ?: throw NullPointerException()
    }

    /**
     * Retrieves the list of [Book] objects.
     */
    fun getBooks() = executeWork(_booksLiveData) {
        getBooksUseCase.getBooks()
    }
}