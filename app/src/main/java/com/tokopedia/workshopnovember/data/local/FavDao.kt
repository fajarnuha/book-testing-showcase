package com.tokopedia.workshopnovember.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {

    @Insert
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun get(): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM FavoriteEntity WHERE isbnId = :id")
    suspend fun delete(id: String)

}
