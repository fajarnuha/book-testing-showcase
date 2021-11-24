package com.tokopedia.workshopnovember

import com.google.gson.Gson
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import com.tokopedia.workshopnovember.entity.search.SearchResponse

class FakeBookApi : BookApi {

    override suspend fun search(query: String): SearchResponse {
        return when(query) {
            "" -> SearchResponse()
            else -> Gson().fromJson(
                FileReader.readStringFromFile("mock_search_lord_response.json"),
                SearchResponse::class.java
            )
        }
    }
    override suspend fun get(id: String): IsbnResponse {
        return Gson().fromJson(
            FileReader.readStringFromFile("mock_detail_harry_response.json"),
            IsbnResponse::class.java
        )
    }

}