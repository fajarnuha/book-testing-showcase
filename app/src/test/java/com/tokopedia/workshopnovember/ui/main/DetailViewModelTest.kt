package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.ui.detail.DetailState
import com.tokopedia.workshopnovember.ui.detail.DetailViewModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
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
        detailViewModel.state.observeForever(observer)
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

        coEvery { repository.getBookById(bookId) } returns bookEntity
        every { repository.getFavorites() } returns flowOf(listOf())

        detailViewModel.setId(bookId)

        verifySequence {
            observer.onChanged(DetailState.Loading)
            observer.onChanged(DetailState.Detail(bookEntity, false))
        }
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