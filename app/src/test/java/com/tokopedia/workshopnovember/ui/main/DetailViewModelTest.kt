package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.entity.FavoriteEntity
import com.tokopedia.workshopnovember.getOrAwaitValue
import com.tokopedia.workshopnovember.ui.detail.DetailState
import com.tokopedia.workshopnovember.ui.detail.DetailViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var repository: BookRepository

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun `when open detail book should return result`()  {
        val bookId = "12345"
        val bookEntity = BookEntity(
            id = bookId,
            isbn = "abc",
            src = "gramedia",
            title = "xyz",
            author = "Josh Barkan",
            timestamp = 123456L
        )
        val favoriteList = flowOf(listOf(FavoriteEntity(
            bookId = bookId
        )))

        coEvery { repository.getBookById(bookId) } returns bookEntity
        coEvery { repository.getFavorites() } returns favoriteList

        detailViewModel.setId(bookId)

        val resultDetail = detailViewModel.state.getOrAwaitValue() as DetailState.Detail
        assertTrue(detailViewModel.state.getOrAwaitValue() is DetailState.Detail)
        assertEquals(resultDetail.data.id, bookEntity.id)
        assertEquals(resultDetail.data.src, bookEntity.src)
        assertEquals(resultDetail.data.isbn, bookEntity.isbn)
    }

    @Test
    fun `open book detail will show loading before showing result`() {
        val bookId = "67890"
        val bookDetailObserver = Observer<DetailState> { }

        mainCoroutineRule.pauseDispatcher()
        detailViewModel.state.observeForever(bookDetailObserver)

        detailViewModel.setId(bookId)

        MatcherAssert.assertThat(detailViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(true))

        mainCoroutineRule.resumeDispatcher()

        MatcherAssert.assertThat(detailViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(false))
        detailViewModel.state.removeObserver(bookDetailObserver)
    }

    @Test
    fun `when setFavorite then repository's setFavorite should be called`() {
        val isChecked = true
        val bookId = "12345"
        coEvery { repository.setFavorite(bookId, isChecked) } just Runs
        detailViewModel.setFavorite(bookId, isChecked)
        coVerify { repository.setFavorite(bookId, isChecked) }
    }

}