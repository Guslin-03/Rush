package com.example.rush.ui.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.model.Restaurant
import com.example.rush.databinding.RestaurantActivityBinding
import com.example.rush.utils.MyApp

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: RestaurantActivityBinding
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val loginUser = MyApp.userPreferences.getUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RestaurantActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantAdapter = RestaurantAdapter(
            ::onRestaurantClickListener
        )

    }

    private fun onRestaurantClickListener(restaurant: Restaurant) {
        if (loginUser != null && restaurant.id != null) {
            //TODO cuando se hace click en el restaurante llevar a la siguiente pantalla que es la de
            //los menus del usuario
        }

    }

}