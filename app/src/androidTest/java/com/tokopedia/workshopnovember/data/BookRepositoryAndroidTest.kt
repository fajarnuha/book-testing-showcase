package com.tokopedia.workshopnovember.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.AppDatabase
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.entity.isbn.Author
import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

/**
 * Non UI Android Test to test repository layer with Room database interaction
 * */
class BookRepositoryAndroidTest {

    private lateinit var sut: BookRepository
    private lateinit var fakeApi: BookApi
    private lateinit var bookDao: BookDao
    private lateinit var favDao: FavDao

    private val room = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()

    private val defaultBook = IsbnResponse(
        isbn13 = listOf("12345"),
        title = "Harry Potter",
        authors = listOf(Author("JK Rowling"))
    )

    @Before
    fun setUp() {
        fakeApi = mockk()
        favDao = room.favDao()
        bookDao = room.bookDao()

        sut = BookRepository(fakeApi, bookDao, favDao)
    }

    @Test
    fun whenHavingCacheThenReturnTheCache() = runBlocking {
        coEvery { fakeApi.get(any()) } returns defaultBook
        val book = sut.getBookById("12345")

        assertEquals(defaultBook.isbn13.first(), book.isbn)
        coVerify { fakeApi.get("12345") }
    }
}