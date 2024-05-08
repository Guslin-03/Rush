package com.example.rush.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.User
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userLocalRepository: RoomUserDataSource
) : ViewModel() {

    private val _login = MutableLiveData<Resource<User>>()
    val login : LiveData<Resource<User>> get() = _login

    private val _first =  MutableLiveData<Resource<Void>>()
    val first : LiveData<Resource<Void>> get() = _first

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
    fun onUpdateFirstTime(id:Int) {
        viewModelScope.launch {
            _first.value = updateFirstTime(id)
        }
    }
    private suspend fun updateFirstTime(id:Int) : Resource<Void> {
        return withContext(IO) {
            userLocalRepository.updateFirst(id)
        }
    }

}

class LoginViewModelFactory(
    private val roomUserRepository: RoomUserDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return LoginViewModel(roomUserRepository) as T
    }

}