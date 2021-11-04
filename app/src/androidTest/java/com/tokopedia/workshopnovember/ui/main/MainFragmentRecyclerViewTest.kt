package com.tokopedia.workshopnovember.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.tokopedia.workshopnovember.FileReader.readStringFromFile
import com.tokopedia.workshopnovember.MainActivity
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.RecyclerViewIdlingResource
import com.tokopedia.workshopnovember.di.DbModule
import com.tokopedia.workshopnovember.di.NetworkModule
import com.tokopedia.workshopnovember.entity.isbn.Author
import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import kotlinx.coroutines.delay
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

const val MOCK_SEARCH_RESPONSE_JSON = "mock_search_response.json"

@UninstallModules(DbModule::class, NetworkModule::class)
@HiltAndroidTest
class MainFragmentRecyclerViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var recyclerView: RecyclerView? = null

    @Before
    fun setUp() {
        stubSearchBook()
        stubGetBookDetail()
    }

    private fun stubSearchBook() {
        val searchResponseString = readStringFromFile(MOCK_SEARCH_RESPONSE_JSON)
        val fakeSearchResponse = Gson().fromJson(searchResponseString, SearchResponse::class.java)

        coEvery {
            FakeDataModule.bookApi.search(any())
        } coAnswers {
            delay(800)
            fakeSearchResponse
        }
    }

    private fun stubGetBookDetail() {
        coEvery {
            FakeDataModule.bookApi.get(any())
        } returns IsbnResponse(
            isbn13 = listOf("12345"),
            title = "Lord of the Rings",
            authors = listOf(Author("JK Rowling"))
        )
    }

    @Test
    fun click_on_a_book_item_will_go_to_detail() {
        launchActivity<MainActivity>().onActivity {
            recyclerView = it.findViewById(R.id.rv)
        }

        onView(withId(R.id.et_search))
            .perform(typeText("lord of the rings"))
            .perform(pressImeActionButton())

        val recyclerViewIdlingResource = RecyclerViewIdlingResource(recyclerView!!)
        IdlingRegistry.getInstance().register(recyclerViewIdlingResource)

        onView(`is`(recyclerView))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.iv_cover)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.cb_fav))
            .check(matches(allOf(isDisplayed(), isNotChecked())))

        IdlingRegistry.getInstance().unregister(recyclerViewIdlingResource)
    }
}