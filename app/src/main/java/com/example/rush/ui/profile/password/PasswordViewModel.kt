package com.example.rush.ui.profile.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordViewModel (private val roomUserDataSource: RoomUserDataSource) : ViewModel() {

    private val _pass = MutableLiveData<Resource<Void>>()
    val pass: LiveData<Resource<Void>> get() = _pass

    fun onUpdatePass(id:Int, newPass:String, oldPass:String) {
        viewModelScope.launch {
            _pass.value = updatePass(id, newPass, oldPass)
        }
    }

    private suspend fun updatePass(id:Int, newPass:String, oldPass:String): Resource<Void> {
        return withContext(Dispatchers.IO) {
            roomUserDataSource.updatePass(id, newPass, oldPass)
        }
    }
}
class PasswordViewModelFactory(
    private val roomUserDataSource: RoomUserDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PasswordViewModel(roomUserDataSource) as T
    }

}