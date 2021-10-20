package com.tokopedia.workshopnovember.ui.detail

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.pojo.BookEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData()
    val state: LiveData<DetailState> = _id.switchMap {
        liveData {
            emit(DetailState.Loading)
            try {
                val result = repository.getBookById(it).toBookEntity()
                repository.getFavorites().collect { favs ->
                    val isFav = favs.map { it.isbnId }.contains(result.isbn)
                    emit(DetailState.Detail(result.copy(isFavorite = isFav)))
                }

            } catch (e: Exception) {
                emit(DetailState.Error(e.message ?: "Something went wrong"))
            }
        }
    }

    fun setId(id: String) {
        _id.value = id
    }

    fun setFavorite(isbn: String, state: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(isbn, state)
        }
    }

}

sealed class DetailState {
    data class Detail(val data: BookEntity) : DetailState()
    data class Error(val msg: String) : DetailState()
    object Loading : DetailState()
}