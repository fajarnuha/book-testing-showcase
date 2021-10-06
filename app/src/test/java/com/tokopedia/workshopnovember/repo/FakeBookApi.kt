package com.tokopedia.workshopnovember.repo

import com.tokopedia.workshopnovember.pojo.search.Doc
import com.tokopedia.workshopnovember.pojo.search.SearchResponse
import com.tokopedia.workshopnovember.repo.cloud.BookApi

class FakeBookApi : BookApi {
    override suspend fun search(query: String): SearchResponse {
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
}