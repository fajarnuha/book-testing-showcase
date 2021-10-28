package com.tokopedia.workshopnovember.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class, BookEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao
    abstract fun bookDao(): BookDao
}