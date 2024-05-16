package com.example.rush.data.repository.restaurant

import androidx.room.Dao
import androidx.room.Insert
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

    override suspend fun getRestaurantById(restaurantId: Int): Resource<Restaurant> {
        val response = restaurantDAO.getRestaurantById(restaurantId).toRestaurant()
        return Resource.success(response)
    }

    override suspend fun createRestaurant(restaurantArray: Array<Restaurant>): Resource<Void> {
        val response = restaurantDAO.createRestaurant(restaurantArray.map {it.toDbRestaurant()})
        if (response.isNotEmpty()) {
            return Resource.success()
        }
        return Resource.error("Ha sucedido un error")
    }

}

fun DbRestaurant.toRestaurant() =
    Restaurant(id, name, originType, specialty, rating,  numberReviews, priceRange, location)
fun Restaurant.toDbRestaurant() =
    DbRestaurant(id, name, originType, specialty, rating, numberReviews, priceRange, location)

@Dao
interface RestaurantDAO {
    @Query("SELECT * FROM restaurants ORDER BY name")
    suspend fun getRestaurants() : List<DbRestaurant>

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId ORDER BY name")
    suspend fun getRestaurantById(restaurantId: Int) : DbRestaurant

    @Insert
    suspend fun createRestaurant(restaurantArray: List<DbRestaurant>) : LongArray

}