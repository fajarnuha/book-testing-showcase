package com.tokopedia.workshopnovember.pojo.isbn


import com.google.gson.annotations.SerializedName

data class Work(
    @SerializedName("key")
    val key: String = ""
)