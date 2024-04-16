package com.example.rush.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.rush.data.model.Menu
import com.example.rush.data.model.Order
import com.example.rush.data.model.Restaurant
import com.example.rush.data.model.User
import com.example.rush.data.repository.menu.MenuType
import com.example.rush.data.repository.menu.RoomMenuDataSource
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.data.repository.restaurant.CulinaryStyle
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.data.repository.restaurant.Specialty
import com.example.rush.data.repository.user.RoomUserDataSource
import kotlinx.coroutines.launch
import java.util.Date

class PopulateDataBase(
    private val userLocalRepository: RoomUserDataSource,
    private val restaurantLocalRepository: RoomRestaurantDataSource,
    private val menuLocalRepository: RoomMenuDataSource,
    private val orderLocalRepository: RoomOrderDataSource,
) : ViewModel() {

    fun toInit() {
        viewModelScope.launch {
            createUsers()
            createRestaurant()
            createMenu()
            createOrder()
        }
    }

    private suspend fun createUsers() {
        userLocalRepository.createUser(
            arrayOf(
                User(1, "David", "Comeron", "davidcomeron@gmail.com", "12341234",
                    601269008, 0, 4036178744314372, true),
                User(2, "Joana", "Barber", "joanabarber@gmail.com", "12341234",
                    601269008,0, 9036171644318222, true)
            )
        )
    }

    private suspend fun createRestaurant() {
        restaurantLocalRepository.createRestaurant(
            arrayOf(
                Restaurant(null, "Misska", CulinaryStyle.ASIATICA.name, Specialty.RAMEN.name,
                    4.5F, 1234, 2,"Colón de Larreátegui K., 35, Abando, 48009 Bilbo, Bizkaia" ),
                Restaurant(null, "GOSE - Smash Burgers", CulinaryStyle.AMERICANA.name, Specialty.HAMBURGUESA.name,
                    4.3F ,235, 3, "Goienkale, 16, Ibaiondo, 48005 Bilbo, Bizkaia"),
                Restaurant(null, "Amaren", CulinaryStyle.ESPAÑOLA.name, Specialty.SIRLOIN.name,
                    4.8F, 4562, 4, "Diputazio Kalea, 6, Abando, 48009 Bilbo, Bizkaia")
            )
        )
    }

    private suspend fun createMenu() {
        menuLocalRepository.createMenu(
            arrayOf(
                Menu(null, "Yakisoba con pollo Karaage", "", 12.00, MenuType.FIRST.name, 1),
                Menu(null, "Poke Bowl Salmón", "", 11.50, MenuType.FIRST.name, 1),
                Menu(null, "Poke Bowl Atún", "", 11.50, MenuType.FIRST.name, 1),
                Menu(null, "Pato caramelizado con semillas de sésamo", "", 15.50, MenuType.FIRST.name, 1),
                Menu(null, "Pollo cítrico crujiente con salsa agridulce", "", 12.90, MenuType.FIRST.name, 1),
                Menu(null, "Ramen Niu Rou Mien con Ternera", "", 12.50, MenuType.FIRST.name, 1),
                Menu(null, "Rollitos Nem de Pollo", "", 5.50, MenuType.STARTERS.name, 1),
                Menu(null, "Gyozas de Cerdo con chives", "", 8.50, MenuType.STARTERS.name, 1),
                Menu(null, "Botella de agua 500ml", "", 2.50, MenuType.DRINK.name, 1),
                Menu(null, "Cervezas Asíaticas", "", 3.50, MenuType.DRINK.name, 1)
            )
        )
    }

    private suspend fun createOrder() {
        orderLocalRepository.createOrder(
            Order(null, Date(), 1,
                listOf(
                    Menu(1, "Yakisoba con pollo Karaage", "", 12.00, MenuType.FIRST.name, 1, 4),
                    Menu(9, "Botella de agua 500ml", "", 2.50, MenuType.DRINK.name, 1,1),
                    Menu(10, "Cervezas Asíaticas", "", 3.50, MenuType.DRINK.name, 1, 2),
                    Menu(5, "Ramen Niu Rou Mien con Ternera", "", 12.50, MenuType.FIRST.name, 1, 2),
                    Menu(6, "Rollitos Nem de Pollo", "", 5.50, MenuType.STARTERS.name, 1, 1),
                )
            )
        )
    }

}

class PopulateDatabaseFactory(
    private val userLocalRepository: RoomUserDataSource,
    private val restaurantLocalRepository: RoomRestaurantDataSource,
    private val menuLocalRepository: RoomMenuDataSource,
    private val orderLocalRepository: RoomOrderDataSource,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PopulateDataBase(
            userLocalRepository,
            restaurantLocalRepository,
            menuLocalRepository,
            orderLocalRepository) as T
    }

}