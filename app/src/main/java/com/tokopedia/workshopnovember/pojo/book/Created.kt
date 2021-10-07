package com.tokopedia.workshopnovember.pojo.book


import com.google.gson.annotations.SerializedName

data class Created(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = ""
)