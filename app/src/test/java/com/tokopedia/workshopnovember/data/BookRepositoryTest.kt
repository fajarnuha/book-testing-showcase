package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.entity.search.Doc
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
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
    fun `given success response then return list of docs`() = runBlockingTest {
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

}
