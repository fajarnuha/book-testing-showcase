package com.tokopedia.workshopnovember.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.test.espresso.idling.CountingIdlingResource
import com.tokopedia.workshopnovember.data.BookRepository
import com.tokopedia.workshopnovember.entity.BookEntity
import com.tokopedia.workshopnovember.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repo: BookRepository,
    countingIdlingResource: CountingIdlingResource? = null
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: LiveData<String> get() = _message

    val favBooks: LiveData<List<BookEntity>> = repo.getFavoritesWithDetail().asLiveData()

    private val _query: MutableLiveData<String> = MutableLiveData()
    val result = _query.switchMap {
        countingIdlingResource?.increment()
        _loading.value = true

        liveData {
            try {
                val books = repo.searchWithQuery(it).map {
                    it.toBookEntity()
                }
                emit(books)
            } catch (e: Exception) {
                _message.value = e.message
            }
            _loading.value = false
            countingIdlingResource?.decrement()
        }
    }

    fun search(q: String) {
        _query.value = q
    }

}