package com.tokopedia.workshopnovember.ui.detail

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.pojo.book.BookResponse
import com.tokopedia.workshopnovember.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _id: MutableLiveData<String> = MutableLiveData()
    val book: LiveData<BookResponse> = _id.switchMap {
        liveData {
            emit(repository.getBookById(it))
        }
    }


    fun setId(id: String) {
        _id.value = id
    }
}