package com.tokopedia.workshopnovember.di

import com.tokopedia.workshopnovember.FakeBookApi
import com.tokopedia.workshopnovember.data.cloud.BookApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class FakeNetworkComponent {

    @Provides
    fun provideBookApi(): BookApi {
        return FakeBookApi()
    }

}