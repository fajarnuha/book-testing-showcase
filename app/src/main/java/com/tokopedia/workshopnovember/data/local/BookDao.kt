package com.tokopedia.workshopnovember.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tokopedia.workshopnovember.pojo.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: BookEntity)

    @Query("SELECT * FROM BookEntity WHERE id == :id")
    fun get(id: String): BookEntity?

}