package com.tokopedia.workshopnovember.repo

import com.tokopedia.workshopnovember.repo.cloud.BookApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookRepositoryTest {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://openlibrary.org").build()
        .create(BookApi::class.java)

    private val sut = BookRepository(api)

    @Test
    fun searchWithQuery() {
        runBlocking {
            val result = sut.searchWithQuery("lord")
            print(result.size)
        }
    }

}