package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.data.local.FavoriteEntity
import com.tokopedia.workshopnovember.pojo.isbn.IsbnResponse
import com.tokopedia.workshopnovember.pojo.search.Doc
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi,
    private val favDao: FavDao,
) {

    suspend fun searchWithQuery(query: String): List<Doc> {
        val cloudResult = bookApi.search(query)
        return cloudResult.docs
    }

    suspend fun getBookById(id: String): IsbnResponse {
        return bookApi.get(id)
    }

    suspend fun setFavorite(id: String, checked: Boolean) {
        if (checked) {
            favDao.insert(FavoriteEntity(isbnId = id))
        } else {
            favDao.delete(id)
        }
    }

    fun getFavorites(): Flow<List<FavoriteEntity>> {
        return favDao.get()
    }

}