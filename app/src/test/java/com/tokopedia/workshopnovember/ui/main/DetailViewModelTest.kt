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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.*

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
    fun `when open detail book should return result`()  {
        TODO("Not yet implemented")
    }

    @Test
    fun `when setFavorite then repository's setFavorite should be called`() {
        coEvery { repository.setFavorite(anyString(), anyBoolean()) } just Runs

        //when
        detailViewModel.setFavorite(anyString(), anyBoolean())

        //then
        coVerify { repository.setFavorite(anyString(), anyBoolean()) }
    }

}