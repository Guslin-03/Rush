package com.example.rush.ui.restaurant.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.Menu
import com.example.rush.data.repository.menu.RoomMenuDataSource
import com.example.rush.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(private val menuDataSource: RoomMenuDataSource
) : ViewModel() {

    private val _menu = MutableLiveData<Resource<List<Menu>>>()
    val menu : LiveData<Resource<List<Menu>>> get() = _menu

    fun onUpdateMenuList(restaurantId: Int?) {
        viewModelScope.launch {
            if (restaurantId != null) {
                _menu.value = updateMenuList(restaurantId)
            }
        }
    }
    private suspend fun updateMenuList(restaurantId: Int) : Resource<List<Menu>> {
        return withContext(Dispatchers.IO) {
            menuDataSource.getMenusByIdRestaurant(restaurantId)
        }
    }

}

class MenuViewModelFactory(
    private val menuDataSource: RoomMenuDataSource
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MenuViewModel(menuDataSource) as T
    }

}