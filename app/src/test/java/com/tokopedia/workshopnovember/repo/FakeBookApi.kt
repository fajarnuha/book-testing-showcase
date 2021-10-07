package com.tokopedia.workshopnovember.repo

import com.tokopedia.workshopnovember.pojo.book.BookResponse
import com.tokopedia.workshopnovember.pojo.search.Doc
import com.tokopedia.workshopnovember.pojo.search.SearchResponse
import com.tokopedia.workshopnovember.repo.cloud.BookApi
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

    override suspend fun get(id: String): BookResponse {
        TODO("Not yet implemented")
    }
}