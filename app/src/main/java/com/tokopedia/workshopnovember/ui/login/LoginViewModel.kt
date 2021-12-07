package com.tokopedia.workshopnovember.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tokopedia.workshopnovember.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepo) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _errorMsg = SingleLiveEvent<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    private val _options = MutableLiveData<LoginOptions>()
    val options: LiveData<LoginOptions> get() = _options

    init {
        viewModelScope.launch {
            val opt = try {
                repo.options()
            } catch (e: Exception) {
                LoginOptions.default()
            }
            _options.value = opt
        }
    }


    fun login(email: String, pass: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val user = repo.login(email, pass)
                _user.value = user
            } catch (e: Exception) {
                _errorMsg.value = e.message
            }
            _loading.value = false
        }
    }
}

sealed class Result<out T : Any> private constructor() {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Fail(val throwable: Throwable) : Result<Nothing>()
}

data class LoginOptions(val options: List<Int> = listOf(0, 1)) {
    companion object {
        fun default(): LoginOptions = LoginOptions()
    }
}

class LoginRepo @Inject constructor() {
    suspend fun login(email: String, pass: String): User {
        delay(1000)
        return User(email, pass)
    }

    suspend fun options(): LoginOptions {
        delay(2000)
        return LoginOptions()
    }
}

data class User(val name: String, val email: String)