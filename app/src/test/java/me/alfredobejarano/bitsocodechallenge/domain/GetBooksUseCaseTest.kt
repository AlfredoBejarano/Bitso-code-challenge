package me.alfredobejarano.bitsocodechallenge.domain

import launchTest
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import me.alfredobejarano.bitsocodechallenge.repository.BookRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBooksUseCaseTest {
    @Mock
    private lateinit var mockRepository: BookRepository

    @Mock
    private lateinit var mockFillBookTickerHistoryDataUseCase: FillBookTickerHistoryDataUseCase

    private lateinit var testCandidate: GetBooksUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testCandidate = GetBooksUseCase(mockRepository, mockFillBookTickerHistoryDataUseCase)
    }

    @Test
    fun getBooks() = launchTest {
        Mockito.`when`(mockRepository.getBooks()).thenReturn(listOf(Book(), Book(), Book()))
        Mockito.`when`(mockFillBookTickerHistoryDataUseCase.fillBookTickerData(Book()))
            .thenReturn(Book(growth = 0.0))

        testCandidate.getBooks().forEach {
            assert(it.growth == 0.0)
        }
    }
}