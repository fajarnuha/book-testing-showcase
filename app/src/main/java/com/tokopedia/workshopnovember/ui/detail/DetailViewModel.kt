package com.tokopedia.workshopnovember.ui.detail

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.pojo.search.SearchResultListUi
import com.tokopedia.workshopnovember.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData()
    val book: LiveData<SearchResultListUi> = _id.switchMap {
        liveData {
            emit(repository.getBookById(it).toUiModel())
        }
    }


    fun setId(id: String) {
        _id.value = id
    }
}