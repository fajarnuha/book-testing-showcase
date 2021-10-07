package com.tokopedia.workshopnovember.pojo.book


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("author")
    val author: AuthorX = AuthorX(),
    @SerializedName("type")
    val type: Type = Type()
)