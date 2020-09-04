package me.alfredobejarano.bitsocodechallenge.domain

import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(private val repository: BookRepository) {
    suspend fun getBooks() = repository.getBooks().sortedWith(compareBy { it.book })
}