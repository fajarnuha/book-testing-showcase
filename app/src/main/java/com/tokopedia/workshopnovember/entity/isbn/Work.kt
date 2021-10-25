package com.tokopedia.workshopnovember.entity.isbn


import com.google.gson.annotations.SerializedName

data class Work(
    @SerializedName("key")
    val key: String = ""
)