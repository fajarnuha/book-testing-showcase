package com.tokopedia.workshopnovember.entity.search


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("docs")
    val docs: List<Doc> = listOf(),
    @SerializedName("numFound")
    val numFound: Int = 0,
    @SerializedName("numFoundExact")
    val numFoundExact: Boolean = false,
    @SerializedName("offset")
    val offset: Any = Any(),
    @SerializedName("q")
    val q: String = "",
    @SerializedName("start")
    val start: Int = 0
)