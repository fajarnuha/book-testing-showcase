package com.tokopedia.workshopnovember.repo

import com.tokopedia.workshopnovember.pojo.isbn.IsbnResponse
import com.tokopedia.workshopnovember.pojo.search.Doc
import com.tokopedia.workshopnovember.repo.cloud.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BookApi) {

    suspend fun searchWithQuery(query: String): List<Doc> {
        val cloudResult = api.search(query)
        return cloudResult.docs
    }

    suspend fun getBookById(id: String): IsbnResponse {
        return api.get(id)
    }

}