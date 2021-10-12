package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.pojo.isbn.IsbnResponse
import com.tokopedia.workshopnovember.pojo.search.Doc
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BookApi,
    private val favDao: FavDao,
) {

    suspend fun searchWithQuery(query: String): List<Doc> {
        val cloudResult = api.search(query)
        return cloudResult.docs
    }

    suspend fun getBookById(id: String): IsbnResponse {
        return api.get(id)
    }

}