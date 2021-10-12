package com.tokopedia.workshopnovember.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDao {

    @Insert
    fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    suspend fun get(): List<FavoriteEntity>

}
