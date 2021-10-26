package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.ui.detail.DetailState
import com.tokopedia.workshopnovember.ui.detail.DetailViewModel
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var repository: BookRepository

    lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun `when open detail book should verify in repository`() = mainCoroutineRule.runBlockingTest {
        val bookId = "12345"
        val bookEntity = BookEntity(
            id = bookId,
            isbn = "abc",
            src = "gramedia",
            title = "xyz",
            author = "Josh Barkan",
            timestamp = 123456L
        )

        val observer = mockk<Observer<DetailState>>()
        val slot = slot<DetailState>()
        val list = arrayListOf<DetailState>()

        detailViewModel.state.observeForever(observer)

        every { observer.onChanged(capture(slot)) } answers {
            list.add(slot.captured)
        }

        coEvery { repository.getBookById(bookId) } coAnswers { bookEntity }

        detailViewModel.setId(bookId)

        coVerify { repository.getBookById(bookId) }
        coVerify { repository.getFavorites() }

        detailViewModel.state.removeObserver(observer)
    }

    @Test
    fun `when setFavorite should verify setFavorite in repository`() {
        val isChecked = true
        val bookId = "12345"
        coEvery { repository.setFavorite(bookId, isChecked) } just Runs
        detailViewModel.setFavorite(bookId, isChecked)
        coVerify { repository.setFavorite(bookId, isChecked) }
    }

}