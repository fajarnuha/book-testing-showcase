package com.tokopedia.workshopnovember.pojo.isbn


import com.google.gson.annotations.SerializedName

data class Created(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    val value: String = ""
)