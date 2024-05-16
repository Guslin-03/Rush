package com.example.rush.ui.restaurant.menu.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.Order
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmationViewModel(private val orderDataSource: RoomOrderDataSource,
    private val restaurantDataSource: RoomRestaurantDataSource
) : ViewModel() {

    private val _order = MutableLiveData<Resource<List<Order>>>()
    val order : LiveData<Resource<List<Order>>> get() = _order

    private val _restaurant = MutableLiveData<Resource<Restaurant>>()
    val restaurant : LiveData<Resource<Restaurant>> get() = _restaurant

    fun onGetRestaurantById(restaurantId: Int) {
        viewModelScope.launch {
            _restaurant.value = getRestaurantById(restaurantId)
        }
    }

    private suspend fun getRestaurantById(restaurantId: Int) : Resource<Restaurant>? {
        return withContext(Dispatchers.IO) {
            restaurantDataSource.getRestaurantById(restaurantId)
        }
    }

}

class ConfirmationViewModelFactory(
    private val orderDataSource: RoomOrderDataSource,
    private val restaurantDataSource: RoomRestaurantDataSource
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ConfirmationViewModel(orderDataSource, restaurantDataSource) as T
    }

}