package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.pojo.search.SearchResponse
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
    private lateinit var mockedDao: FavDao
    private lateinit var sut: BookRepository

    @Before
    fun setup() {
        api = mockk()
        mockedDao = mockk()

        sut = BookRepository(api, mockedDao)
    }

    @Test
    fun `given success response then return list of docs`() = runBlockingTest {
        val q = "foo"
        val fakeResponse = SearchResponse(q = q)
        coEvery { api.search(q) } returns fakeResponse

        val actual = sut.searchWithQuery(q)

        assertEquals(fakeResponse.docs, actual)
    }

}
