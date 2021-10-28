package com.tokopedia.workshopnovember.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
    val id: String,
    val isbn: String,
    val src: String,
    val title: String,
    val author: String?,
    val timestamp: Long,
    val publisher: String? = null,
    val publishDate: String? = null,
)