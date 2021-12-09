package com.tokopedia.workshopnovember.ui.login

import androidx.lifecycle.*
import com.tokopedia.workshopnovember.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepo) : ViewModel() {

    private val prev: Stack<LoginState> = Stack()

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    private val _errorMsg = SingleLiveEvent<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    init {
        _state.value = LoginState.LoginDisplay()
        viewModelScope.launch {
            repo.options().collect {
                if (_state.value is LoginState.LoginDisplay) {
                    _state.value = (_state.value as LoginState.LoginDisplay).copy(options = it)
                }
                // ignore if it is not in LoginDisplay state
            }
        }

    }

    fun login(email: String, pass: String) {
        prev.push(_state.value)
        _state.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val user = repo.login(email, pass)
                _state.value = LoginState.UserDisplay(user)
            } catch (e: Exception) {
                _errorMsg.value = e.message
                _state.value = prev.pop()
            }
        }
    }
}

sealed class LoginState {

    data class UserDisplay(
        val user: User
    ) : LoginState()

    data class LoginDisplay(
        val form: Pair<String, String> = "" to "",
        val options: LoginOptions = LoginOptions.default()
    ) : LoginState()

    object Loading : LoginState()
}

data class LoginOptions(val options: List<Int> = listOf(0, 1)) {
    companion object {
        fun default(): LoginOptions = LoginOptions(options = listOf(0))
    }
}


class LoginRepo @Inject constructor() {
    suspend fun login(email: String, pass: String): User {
        delay(1000)
        return User(email, pass)
    }

    fun options(): Flow<LoginOptions> {
        return flow {
            // network
            delay(2000)
            emit(LoginOptions())
        }
            .catch { e -> e.printStackTrace() }
            .onStart { emit(LoginOptions.default()) }
    }
}

data class User(val name: String, val email: String)