package com.example.rush.ui.profile.card

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

class CardViewModel (private val roomUserDataSource: RoomUserDataSource) : ViewModel() {

    private val _card = MutableLiveData<Resource<Void>>()
    val card: LiveData<Resource<Void>> get() = _card

    fun onUpdateCard(id:Int, cardNumber:Long) {
        viewModelScope.launch {
            _card.value = updateCard(id, cardNumber)
        }
    }

    private suspend fun updateCard(id:Int, cardNumber:Long): Resource<Void> {
        return withContext(Dispatchers.IO) {
            roomUserDataSource.updateCard(id, cardNumber)
        }
    }
}
class CardViewModelFactory(
    private val roomUserDataSource: RoomUserDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CardViewModel(roomUserDataSource) as T
    }

}