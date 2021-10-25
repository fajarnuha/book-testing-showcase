package com.tokopedia.workshopnovember.data.cloud

import com.tokopedia.workshopnovember.entity.isbn.IsbnResponse
import com.tokopedia.workshopnovember.entity.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("/search.json?limit=25")
    suspend fun search(@Query("q") query: String): SearchResponse

    @GET("/isbn/{id}.json")
    suspend fun get(@Path("id") id: String): IsbnResponse

}