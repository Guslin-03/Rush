package com.example.rush.ui.welcome

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.repository.PopulateDataBase
import com.example.rush.data.repository.PopulateDatabaseFactory
import com.example.rush.data.repository.menu.RoomMenuDataSource
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.databinding.WelcomeActivityBinding
import com.example.rush.utils.MyApp

class WelcomeActivity  : AppCompatActivity(){
    private lateinit var binding: WelcomeActivityBinding
    private val userRepository = RoomUserDataSource()
    private val restaurantRepository = RoomRestaurantDataSource()
    private val menuRepository = RoomMenuDataSource()
    private val orderRepository = RoomOrderDataSource()
    private val populateDataBase: PopulateDataBase by viewModels {
        PopulateDatabaseFactory(
            userRepository, restaurantRepository, menuRepository, orderRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!MyApp.userPreferences.getSaveDataBaseIsCreated()) populateDataBase.toInit()

    }
}