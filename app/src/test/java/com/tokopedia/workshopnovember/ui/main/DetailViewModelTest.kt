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
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
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

    lateinit var repository: BookRepository

    private lateinit var observer: Observer<DetailState>

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        repository = mockk()
        observer = mockk(relaxUnitFun = true)
        detailViewModel = DetailViewModel(repository)
    }

    @After
    fun finish() {
        detailViewModel.state.removeObserver(observer)
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

        detailViewModel.state.observeForever(observer)

        coEvery { repository.getBookById(bookId) } returns bookEntity
        every { repository.getFavorites() } returns favoriteList

        detailViewModel.setId(bookId)

        verifySequence {
            observer.onChanged(DetailState.Loading)
            observer.onChanged(DetailState.Detail(bookEntity, false))
        }
    }

//    @Test
//    fun `open book detail will show loading before showing result`() {
//        val bookId = "67890"
//
//        mainCoroutineRule.pauseDispatcher()
//        detailViewModel.state.observeForever(observer)
//
//        detailViewModel.setId(bookId)
//
//        MatcherAssert.assertThat(detailViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(true))
//
//        mainCoroutineRule.resumeDispatcher()
//
//        MatcherAssert.assertThat(detailViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(false))
//    }

    @Test
    fun `when setFavorite then repository's setFavorite should be called`() {
        val isChecked = true
        val bookId = "12345"
        coEvery { repository.setFavorite(bookId, isChecked) } just Runs
        detailViewModel.setFavorite(bookId, isChecked)
        coVerify { repository.setFavorite(bookId, isChecked) }
    }

}