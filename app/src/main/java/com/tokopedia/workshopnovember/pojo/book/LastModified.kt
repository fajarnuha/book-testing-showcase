package com.tokopedia.workshopnovember.pojo.book


import com.google.gson.annotations.SerializedName

data class LastModified(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = ""
)