package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.ui.detail.DetailState
import com.tokopedia.workshopnovember.ui.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    private val repository: BookRepository = mockk()

    private val observer: Observer<DetailState> = mockk(relaxUnitFun = true)

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(repository)
        detailViewModel.state.observeForever(observer)
    }

    @After
    fun finish() {
        detailViewModel.state.removeObserver(observer)
    }

    @Test
    fun `when open detail book should return result`() {
        //given
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

        //when
        detailViewModel.setId(bookId)

        //then
        verifySequence {
            observer.onChanged(DetailState.Loading)
            observer.onChanged(DetailState.Detail(bookEntity, false))
        }
    }
}