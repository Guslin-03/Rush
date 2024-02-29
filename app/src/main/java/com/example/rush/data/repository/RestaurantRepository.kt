package com.example.rush.data.repository

import com.example.rush.data.model.Restaurant
import com.example.rush.utils.Resource

interface RestaurantRepository {
    suspend fun getRestaurants(): Resource<List<Restaurant>>
}