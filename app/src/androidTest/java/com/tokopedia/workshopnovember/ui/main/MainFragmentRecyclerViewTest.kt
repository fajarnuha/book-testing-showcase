package com.tokopedia.workshopnovember.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.tokopedia.workshopnovember.MainActivity
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.utils.SimpleIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainFragmentRecyclerViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favDao: FavDao

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(SimpleIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(SimpleIdlingResource.countingIdlingResource)
    }

    @Test
    fun when_add_book_to_favorite_the_book_should_be_shown_in_favourite_list() {
        // Ideally we use orchestrator to clear data, but it's out of our scope
        favDao.deleteAll()

        launchActivity<MainActivity>()

        onView(withId(R.id.et_search))
            .perform(typeText("harry potter"))
            .perform(pressImeActionButton())

        onView(withId(R.id.rv))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(containsString("Azkaban"))), click())
            )

        onView(withId(R.id.iv_cover)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.cb_fav))
            .check(matches(allOf(isDisplayed(), isNotChecked())))
            .perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.et_search))
            .perform(clearText())
            .perform(pressImeActionButton())

        onView(withId(R.id.fav_rv))
            .check(matches(hasDescendant(withText("Harry Potter and the Prisoner of Azkaban"))))
    }
}