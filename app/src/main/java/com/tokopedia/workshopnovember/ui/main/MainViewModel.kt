package com.tokopedia.workshopnovember.ui.main

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: BookRepository): ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    // should be using SingleLiveEvent, but skipped for this project
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> get() = _message

    private val _query: MutableLiveData<String> = MutableLiveData()
    val result = _query.switchMap {
        liveData {
            _loading.value = true
            try {
                val books = repo.searchWithQuery(it).map {
                    it.toUiModel()
                }
                emit(books)
            } catch (e: Exception) {
                _message.value = "something went wrong"
            }
            _loading.value = false
        }
    }

    fun search(q: String) {
        _query.value = q
    }

}