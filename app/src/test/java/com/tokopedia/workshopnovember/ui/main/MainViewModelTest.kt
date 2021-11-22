package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var repo: BookRepository
    lateinit var sut: MainViewModel

    @Before
    fun setUp() {
        repo = mockk {
            every { this@mockk.getFavoritesWithDetail() } returns flowOf()
        }
        sut = MainViewModel(repo)
    }

    @Test
    fun `search will show loading before showing results`() {
        val bookListObserver = Observer<List<BookEntity>> { }
        sut.result.observeForever(bookListObserver)

        mainCoroutineRule.pauseDispatcher()

        sut.search("Harry")

        assertThat(sut.loading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(sut.loading.getOrAwaitValue(), `is`(false))

        sut.result.removeObserver(bookListObserver)
    }
}