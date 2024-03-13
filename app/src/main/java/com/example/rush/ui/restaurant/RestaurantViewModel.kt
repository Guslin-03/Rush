package com.example.rush.ui.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel(private val restaurantDataSource: RoomRestaurantDataSource
) : ViewModel() {

    private val _restaurant = MutableLiveData<Resource<List<Restaurant>>>()
    val restaurant : LiveData<Resource<List<Restaurant>>> get() = _restaurant

    init { onUpdateRestaurantList() }

    private fun onUpdateRestaurantList() {
        viewModelScope.launch {
            _restaurant.value = updateRestaurantList()
        }
    }
    private suspend fun updateRestaurantList() : Resource<List<Restaurant>> {
        return withContext(IO) {
            restaurantDataSource.getRestaurants()
        }
    }

}

class RestaurantViewModelFactory(
    private val restaurantDataSource: RoomRestaurantDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RestaurantViewModel(restaurantDataSource) as T
    }

}