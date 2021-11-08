package com.tokopedia.workshopnovember.ui.detail

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.tokopedia.workshopnovember.R
import com.tokopedia.workshopnovember.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun launch_detail_fragment() {
        val bundle = Bundle().apply { putString("ARGS_ID", "074757362X") }
        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs = bundle, themeResId = R.style.Theme_WorkshopNovember)

        Thread.sleep(5000)
    }
}