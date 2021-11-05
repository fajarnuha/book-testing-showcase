package com.tokopedia.workshopnovember.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.tokopedia.workshopnovember.MainActivity
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.RecyclerViewIdlingResource
import com.tokopedia.workshopnovember.di.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class MainFragmentRecyclerViewTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var recyclerView: RecyclerView? = null

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