package com.tokopedia.workshopnovember.di

import android.content.Context
import androidx.room.Room
import com.tokopedia.workshopnovember.data.local.AppDatabase
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "appDb")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFavDao(db: AppDatabase): FavDao {
        return db.favDao()
    }

    @Provides
    fun provideBookDao(db: AppDatabase): BookDao {
        return db.bookDao()
    }

}