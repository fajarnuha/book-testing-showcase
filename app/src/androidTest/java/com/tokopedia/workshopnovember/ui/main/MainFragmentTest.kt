package com.tokopedia.workshopnovember.ui.main

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class MainFragmentTest {

    @Test
    fun launch_main_fragment_will_show_search_bar() {
        launchFragmentInHiltContainer<MainFragment>(Bundle(), R.style.Theme_WorkshopNovember)

        onView(withId(R.id.et_search))
            .check(matches(isDisplayed()))
    }

    @Test
    fun typing_on_search_bar_and_press_enter() {
        launchFragmentInHiltContainer<MainFragment>(Bundle(), R.style.Theme_WorkshopNovember)

        onView(withId(R.id.et_search))
            .perform(typeText("harry"))
            .perform(pressImeActionButton())
    }
}