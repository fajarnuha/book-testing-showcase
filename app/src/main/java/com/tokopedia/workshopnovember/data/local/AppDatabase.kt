package com.tokopedia.workshopnovember.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tokopedia.workshopnovember.pojo.BookEntity
import com.tokopedia.workshopnovember.pojo.FavoriteEntity

@Database(entities = [FavoriteEntity::class, BookEntity::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao
    abstract fun bookDao(): BookDao
}