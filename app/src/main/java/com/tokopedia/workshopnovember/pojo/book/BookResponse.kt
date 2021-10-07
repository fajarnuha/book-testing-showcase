package com.tokopedia.workshopnovember.pojo.book


import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("authors")
    val authors: List<Author> = listOf(),
    @SerializedName("covers")
    val covers: List<Int> = listOf(),
    @SerializedName("created")
    val created: Created = Created(),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("key")
    val key: String = "",
    @SerializedName("last_modified")
    val lastModified: LastModified = LastModified(),
    @SerializedName("latest_revision")
    val latestRevision: Int = 0,
    @SerializedName("revision")
    val revision: Int = 0,
    @SerializedName("subject_people")
    val subjectPeople: List<String> = listOf(),
    @SerializedName("subject_places")
    val subjectPlaces: List<String> = listOf(),
    @SerializedName("subject_times")
    val subjectTimes: List<String> = listOf(),
    @SerializedName("subjects")
    val subjects: List<String> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: TypeX = TypeX()
)