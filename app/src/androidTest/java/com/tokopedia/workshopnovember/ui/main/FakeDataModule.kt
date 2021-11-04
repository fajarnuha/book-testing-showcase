package com.tokopedia.workshopnovember.ui.main

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.AppDatabase
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.di.DbModule
import com.tokopedia.workshopnovember.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk

@Module
@TestInstallIn(
    replaces = [NetworkModule::class, DbModule::class],
    components = [SingletonComponent::class],
)
object FakeDataModule {

    private val room = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()
    val bookApi: BookApi = mockk()
    val favDao: FavDao = room.favDao()
    val bookDao: BookDao = room.bookDao()

    @Provides
    fun provideBookApi(): BookApi = bookApi

    @Provides
    fun provideBookDao(): BookDao = bookDao

    @Provides
    fun provideFavDao(): FavDao = favDao
}