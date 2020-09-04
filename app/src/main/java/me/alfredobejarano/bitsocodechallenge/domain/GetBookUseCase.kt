package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetBookUseCase @Inject constructor(private val repository: BookRepository) {
    suspend fun getBook(book: String) = repository.getBook(book)
}