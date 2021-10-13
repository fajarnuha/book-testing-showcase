package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.FavDao
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * This is a network test to quickly get the API's response without the need to launch Android Emulator.
 * Convenience when in development/ debugging phase
 * */
class BookRepositoryNetworkTest {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://openlibrary.org").build()
        .create(BookApi::class.java)

    private val mockedDao = mockk<FavDao>()

    private val sut = BookRepository(api, mockedDao)

    @Test
    fun searchWithQuery() {
        runBlocking {
            val result = sut.searchWithQuery("lord of the ring")
            val listOfFirstAuthor = result.map {
                it.authorName?.first()
            }

            val listOfFirstIsbn = result.map {
                it.isbn?.first()
            }
            print(
                """author nulls: ${listOfFirstAuthor.filter { it == null }.size}
                    |isbn nulls: ${listOfFirstIsbn.filter { it == null }.size}""".trimMargin()
            )

            assertEquals(25, result.size)
        }
    }

    @Test
    fun `book id`() = runBlocking {
        val id = "9780140328721"

        val result = sut.getBookById(id)

        print(result)
    }

}