package com.tokopedia.workshopnovember.pojo.isbn


import com.google.gson.annotations.SerializedName
import com.tokopedia.workshopnovember.pojo.BookEntity

data class IsbnResponse(
    @SerializedName("authors")
    val authors: List<Author> = listOf(),
    @SerializedName("classifications")
    val classifications: Classifications = Classifications(),
    @SerializedName("contributions")
    val contributions: List<String> = listOf(),
    @SerializedName("covers")
    val covers: List<Int> = listOf(),
    @SerializedName("created")
    val created: Created = Created(),
    @SerializedName("first_sentence")
    val firstSentence: FirstSentence = FirstSentence(),
    @SerializedName("identifiers")
    val identifiers: Identifiers = Identifiers(),
    @SerializedName("isbn_10")
    val isbn10: List<String> = listOf(),
    @SerializedName("isbn_13")
    val isbn13: List<String> = listOf(),
    @SerializedName("key")
    val key: String = "",
    @SerializedName("languages")
    val languages: List<Language> = listOf(),
    @SerializedName("last_modified")
    val lastModified: LastModified = LastModified(),
    @SerializedName("latest_revision")
    val latestRevision: Int = 0,
    @SerializedName("local_id")
    val localId: List<String> = listOf(),
    @SerializedName("number_of_pages")
    val numberOfPages: Int = 0,
    @SerializedName("ocaid")
    val ocaid: String = "",
    @SerializedName("publish_date")
    val publishDate: String = "",
    @SerializedName("publishers")
    val publishers: List<String> = listOf(),
    @SerializedName("revision")
    val revision: Int = 0,
    @SerializedName("source_records")
    val sourceRecords: List<String> = listOf(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: Type = Type(),
    @SerializedName("works")
    val works: List<Work> = listOf()
) {
    fun toBookEntity(): BookEntity {
        return BookEntity(
            isbn13.first(),
            "https://covers.openlibrary.org/b/isbn/${isbn13.first()}-M.jpg",
            title,
            authors.firstOrNull()?.key,
        )
    }
}