package com.tokopedia.workshopnovember

import com.google.gson.Gson
import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import kotlinx.coroutines.delay

class FakeBookApi : BookApi {

    override suspend fun search(query: String): SearchResponse {
        return Gson().fromJson(
            FileReader.readStringFromFile("mock_search_lord_response.json"),
            SearchResponse::class.java
        )
    }

    override suspend fun get(id: String): IsbnResponse {
        // Having a delay to simulate real network (network won't have 0ms latency)
        delay(50)
        return Gson().fromJson(
            FileReader.readStringFromFile("mock_detail_harry_response.json"),
            IsbnResponse::class.java
        )
    }

}