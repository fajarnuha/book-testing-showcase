package com.tokopedia.workshopnovember.ui.detail

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.pojo.search.BookUiModel
import com.tokopedia.workshopnovember.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData()
    val book: LiveData<DetailState> = _id.switchMap {
        liveData {
            emit(DetailState.Loading)
            try {
                val result = repository.getBookById(it).toUiModel()
                emit(DetailState.Detail(result))
            } catch (e: Exception) {
                emit(DetailState.Error(e.message ?: "Something went wrong"))
            }
        }
    }

    fun setId(id: String) {
        _id.value = id
    }

}

sealed class DetailState {
    data class Detail(val data: BookUiModel) : DetailState()
    data class Error(val msg: String) : DetailState()
    object Loading : DetailState()
}