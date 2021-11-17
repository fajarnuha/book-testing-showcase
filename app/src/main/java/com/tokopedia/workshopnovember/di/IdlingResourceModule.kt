package com.tokopedia.workshopnovember.di

import androidx.test.espresso.idling.CountingIdlingResource
import com.tokopedia.workshopnovember.utils.SimpleIdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object IdlingResourceModule {

    @Provides
    fun provideCountingIdlingResource(): CountingIdlingResource {
        return SimpleIdlingResource.countingIdlingResource
    }
}