package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.entity.FavoriteEntity
import com.tokopedia.workshopnovember.entity.search.Doc
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BookRepositoryTest {

    private lateinit var api: BookApi
    private lateinit var fakeFavDao: FavDao
    private lateinit var fakeBookDao: BookDao
    private lateinit var sut: BookRepository

    @Before
    fun setup() {
        api = mockk()
        fakeFavDao = mockk()
        fakeBookDao = mockk()

        sut = BookRepository(api, fakeBookDao, fakeFavDao)
    }

    @Test
    fun `given success response then return list of docs`() = runBlocking {
        // given
        val query = "foo"
        val expectedBook = listOf(Doc(title = "Harry Potter"))
        val fakeResponse = SearchResponse(q = query, docs = expectedBook)
        coEvery { api.search(query) } returns fakeResponse

        // when
        val actual = sut.searchWithQuery(query)

        // then
        assertEquals(expectedBook, actual)
    }

    @Test
    fun `when setFavorite given checked state is true should verify insert in favDao`() = runBlocking {
        val isChecked = true
        val bookId = "12345"
        coEvery { fakeFavDao.insert(FavoriteEntity(bookId = bookId)) } just Runs
        sut.setFavorite(bookId, isChecked)
        coVerify { fakeFavDao.insert(FavoriteEntity(bookId = bookId)) }
    }


    @Test
    fun `when setFavorite given checked state is false should verify delete in favDao`() = runBlocking {
        val isChecked = false
        val bookId = "12345"
        coEvery { fakeFavDao.delete(bookId) } just Runs
        sut.setFavorite(bookId, isChecked)
        coVerify { fakeFavDao.delete(bookId) }
    }

}
