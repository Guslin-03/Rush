package com.example.rush.data.repository.restaurant

import androidx.room.Dao
import androidx.room.Query
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.RestaurantRepository
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RoomRestaurantDataSource: RestaurantRepository {

    private val restaurantDAO: RestaurantDAO = MyApp.db.restaurantDAO()

    override suspend fun getRestaurants(): Resource<List<Restaurant>> {
        val response = restaurantDAO.getRestaurants().map { it.toRestaurant() }
        return Resource.success(response)
    }

}

fun DbRestaurant.toRestaurant() = Restaurant(id, name, location)
fun Restaurant.toDbRestaurant() = DbRestaurant(id, name, location)

@Dao
interface RestaurantDAO {
    @Query("SELECT * FROM restaurants ORDER BY name")
    suspend fun getRestaurants() : List<DbRestaurant>

}