package me.alfredobejarano.bitsocodechallenge.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.alfredobejarano.bitsocodechallenge.domain.GetBooksUseCase

class BookListViewModel @ViewModelInject constructor(private val getBooksUseCase: GetBooksUseCase) :
    ViewModel() {

    /**
     * Retrieves the list of books.
     */
    fun getBooks() = liveData(Dispatchers.IO) {
        emit(getBooksUseCase.getBooks())
    }
}