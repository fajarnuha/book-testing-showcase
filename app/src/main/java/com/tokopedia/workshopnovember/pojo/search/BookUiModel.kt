package com.tokopedia.workshopnovember.pojo.search

data class BookUiModel(
    val isbn: String?,
    val src: String,
    val title: String,
    val author: String?,
    val workId: String,
    val isFavorite: Boolean = false,
)