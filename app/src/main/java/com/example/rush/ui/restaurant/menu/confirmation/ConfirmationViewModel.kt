package com.example.rush.ui.restaurant.menu.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.Order
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmationViewModel(private val orderDataSource: RoomOrderDataSource
) : ViewModel() {

    private val _order = MutableLiveData<Resource<List<Order>>>()
    val order : LiveData<Resource<List<Order>>> get() = _order

    fun onUpdateOrderList(orderId: Int?) {
        viewModelScope.launch {
            if (orderId != null) {
                _order.value = updateOrderList(orderId)
            }
        }
    }
    private suspend fun updateOrderList(orderId: Int) : Resource<List<Order>>? {
        return withContext(Dispatchers.IO) {
//            orderDataSource.getMenusByIdRestaurant(orderId)
            null
        }
    }

}

class ConfirmationViewModelFactory(
    private val orderDataSource: RoomOrderDataSource
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ConfirmationViewModel(orderDataSource) as T
    }

}