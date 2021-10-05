package com.tokopedia.workshopnovember.repo.cloud

import com.tokopedia.workshopnovember.pojo.search.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("/search.json?limit=20")
    suspend fun search(@Query("q") query: String): SearchResult

}