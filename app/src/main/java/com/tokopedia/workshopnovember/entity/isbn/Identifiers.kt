package com.tokopedia.workshopnovember.entity.isbn


import com.google.gson.annotations.SerializedName

data class Identifiers(
    @SerializedName("goodreads")
    val goodreads: List<String> = listOf(),
    @SerializedName("librarything")
    val librarything: List<String> = listOf()
)