package me.alfredobejarano.bitsocodechallenge.datasource.local

import android.os.Build
import androidx.room.Room
import launchTest
import me.alfredobejarano.bitsocodechallenge.model.local.Book
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * BookDatabaseTest
 */
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.LOLLIPOP])
@RunWith(RobolectricTestRunner::class)
class BookDaoTest {
    private lateinit var testCandidate: BookDao

    @Before
    fun setup() {
        testCandidate = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.systemContext,
            BookDatabase::class.java
        ).build().provideBookDao()
    }

    @Test
    fun createTest() = launchTest {
        val testId = "btc_mxn"
        val testItem = Book(book = testId)
        assert(testCandidate.readAll()?.isEmpty() == true)
        testCandidate.createOrUpdate(testItem)
        assert(testCandidate.read(testId) == testItem)
    }

    @Test
    fun readTest() = launchTest {
        assert(testCandidate.readAll()?.isEmpty() == true)
        testCandidate.createOrUpdate(Book(book = "btc_mxn"))
        assert(testCandidate.readAll()?.size == 1)
    }

    @Test
    fun readAllTest() = launchTest {
        assert(testCandidate.readAll()?.isEmpty() == true)
        for (i in 0 until 2) {
            testCandidate.createOrUpdate(Book(book = "$i"))
        }
        assert(testCandidate.readAll()?.size == 2)
    }

    @Test
    fun updateTest() = launchTest {
        var testItem = Book(book = "btc_mxn", lastPrice = 100.0)
        testCandidate.createOrUpdate(testItem)
        var test = testCandidate.read("btc_mxn")
        assert(test?.lastPrice == 100.0)

        testItem = Book(book = "btc_mxn", lastPrice = 150.0)
        testCandidate.createOrUpdate(testItem)
        test = testCandidate.read("btc_mxn")
        assert(test?.lastPrice == 150.0)
    }
}