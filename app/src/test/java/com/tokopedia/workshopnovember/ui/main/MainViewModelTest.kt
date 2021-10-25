package com.tokopedia.workshopnovember.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tokopedia.workshopnovember.MainCoroutineRule
import com.tokopedia.workshopnovember.getOrAwaitValue
import com.tokopedia.workshopnovember.entity.search.Doc
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var repo: BookRepository
    lateinit var sut: MainViewModel

    @Before
    fun setUp() {
        repo = mockk()
        sut = MainViewModel(repo)
    }

    @Test
    fun search() {
        val list = listOf(Doc(title = "Harry Potter"))
        coEvery { repo.searchWithQuery(any()) } returns list

        sut.search("harry")

        assertThat(sut.result.getOrAwaitValue().first().title, `is`(list.first().title))
    }

    @Test
    fun `search will show loading before showing results`() {
        val bookListObserver = Observer<List<BookEntity>> { }
        sut.result.observeForever(bookListObserver)

        mainCoroutineRule.pauseDispatcher()

        sut.search("harry")

        assertThat(sut.loading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(sut.loading.getOrAwaitValue(), `is`(false))

        sut.result.removeObserver(bookListObserver)
    }

}