package com.tokopedia.workshopnovember.pojo

data class BookEntity(
    val isbn: String?,
    val src: String,
    val title: String,
    val author: String?,
    val workId: String,
    val isFavorite: Boolean = false,
)