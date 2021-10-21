package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.data.local.FavoriteEntity
import com.tokopedia.workshopnovember.pojo.BookEntity
import com.tokopedia.workshopnovember.pojo.search.Doc
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val favDao: FavDao,
) {

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }

    suspend fun searchWithQuery(query: String): List<Doc> {
        val cloudResult = bookApi.search(query)
        return cloudResult.docs
    }

    suspend fun getBookById(id: String): BookEntity {
        bookDao.get(id)?.let {
            if (!it.timestamp.isExpired()) {
                return it
            }
        }
        val freshBook = bookApi.get(id).toBookEntity()
        bookDao.insert(freshBook)
        return freshBook
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

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

}