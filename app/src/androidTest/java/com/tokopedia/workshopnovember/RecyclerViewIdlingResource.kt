package com.tokopedia.workshopnovember

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

class RecyclerViewIdlingResource(
    private val recyclerView: RecyclerView
): IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String =
        "Recycler view idling resource - ${recyclerView.id}"

    override fun isIdleNow(): Boolean {
        val recyclerView = recyclerView

        val isIdle = recyclerView.adapter.hasItem()
        if (isIdle) resourceCallback?.onTransitionToIdle()

        return isIdle
    }

    private fun RecyclerView.Adapter<*>?.hasItem(): Boolean {
        return this != null && this.itemCount > 0
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }
}