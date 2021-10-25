package com.tokopedia.workshopnovember.data

import com.tokopedia.workshopnovember.data.cloud.BookApi
import com.tokopedia.workshopnovember.data.local.BookDao
import com.tokopedia.workshopnovember.data.local.FavDao
import com.tokopedia.workshopnovember.entity.FavoriteEntity
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.entity.search.Doc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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

    suspend fun getBookById(id: String): BookEntity = withContext(Dispatchers.IO) {
        bookDao.get(id)?.let {
            if (!it.timestamp.isExpired()) {
                return@withContext it
            }
        }
        val freshBook = bookApi.get(id).toBookEntity(id)
        bookDao.insert(freshBook)
        freshBook
    }

    suspend fun setFavorite(id: String, checked: Boolean) {
        if (checked) {
            favDao.insert(FavoriteEntity(bookId = id))
        } else {
            favDao.delete(id)
        }
    }

    fun getFavorites(): Flow<List<FavoriteEntity>> {
        return favDao.get()
    }

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

}