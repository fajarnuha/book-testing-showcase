package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.entity.search.Doc
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import kotlinx.coroutines.delay

class FakeBookApi : BookApi {
    override suspend fun search(query: String): SearchResponse {
        delay(2000)
        return SearchResponse(
            docs = listOf(
                Doc(
                    title = "Harry Potter",
                    authorName = listOf("JK Rowling"),
                )
            ),
            q = query,
            numFound = 20,
        )
    }

    override suspend fun get(id: String): IsbnResponse {
        TODO("Not yet implemented")
    }
}