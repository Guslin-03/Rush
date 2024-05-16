package com.example.rush.data.repository

import com.example.rush.data.model.Restaurant
import com.example.rush.utils.Resource

interface RestaurantRepository {
    suspend fun getRestaurants(): Resource<List<Restaurant>>
    suspend fun getRestaurantById(restaurantId: Int): Resource<Restaurant>
    suspend fun createRestaurant(restaurantArray: Array<Restaurant>): Resource<Void>
}