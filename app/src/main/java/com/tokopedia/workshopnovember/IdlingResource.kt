package com.tokopedia.workshopnovember

import androidx.test.espresso.idling.CountingIdlingResource

interface IdlingResourceHolder {

    fun getCountingIdlingResource(): CountingIdlingResource?
}