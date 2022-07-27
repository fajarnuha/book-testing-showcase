package com.tokopedia.workshopnovember.di

import android.content.Context
import androidx.room.Room
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.AppDatabase
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "https://openlibrary.org"

    @Provides
    fun provideRetrofit(@Named("BASE_URL") url: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .baseUrl(url).build()
    }

    @Provides
    fun provideBookApi(retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

}