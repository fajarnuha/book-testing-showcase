package com.tokopedia.workshopnovember.ui.main

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.tokopedia.workshopnovember.MainActivity
import com.tokopedia.workshopnovember.R
import org.junit.Test

class MainActivityTest {

    @Test
    fun launch_main_activity_will_show_search_bar() {
        launchActivity<MainActivity>()

        onView(withId(R.id.et_search))
            .check(matches(isDisplayed()))
    }

    @Test
    fun typing_on_search_bar_and_press_enter() {
        launchActivity<MainActivity>()

        onView(withId(R.id.et_search))
            .perform(typeText("harry"))
            .perform(pressImeActionButton())
    }
}