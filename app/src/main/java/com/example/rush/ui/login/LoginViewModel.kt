package com.example.rush.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.User
import com.example.rush.data.repository.user.DbUser
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userLocalRepository: RoomUserDataSource
) : ViewModel() {

    private val _login = MutableLiveData<Resource<User>>()
    val login : LiveData<Resource<User>> get() = _login

    fun onLogin(userName: String, password: String) {
        viewModelScope.launch {
            _login.value  = login(userName, password)
        }
    }
    private suspend fun login(userName: String, password: String) : Resource<User> {
        return withContext(IO) {
            userLocalRepository.login(userName, password)
        }
    }

}

class LoginViewModelFactory(
    private val roomUserRepository: RoomUserDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return LoginViewModel(roomUserRepository) as T
    }

}