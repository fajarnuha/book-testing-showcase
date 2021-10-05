package com.tokopedia.workshopnovember.ui.main

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.pojo.search.SearchResultListUi
import com.tokopedia.workshopnovember.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repo: BookRepository): ViewModel() {

    private val _query: MutableLiveData<String> = MutableLiveData()

    val result = _query.switchMap {
        liveData { emit(repo.searchWithQuery(it).map {
            SearchResultListUi(
                it.isbn?.firstOrNull(),
                "http://covers.openlibrary.org/b/isbn/${it.isbn?.firstOrNull()}-M.jpg",
                it.title,
                it.author_name?.firstOrNull()
            )
        }) }
    }

    fun search(q: String) {
        _query.value = q
    }

}