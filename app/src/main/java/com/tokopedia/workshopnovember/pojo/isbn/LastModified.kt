package com.tokopedia.workshopnovember.pojo.isbn


import com.google.gson.annotations.SerializedName

data class LastModified(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = ""
)