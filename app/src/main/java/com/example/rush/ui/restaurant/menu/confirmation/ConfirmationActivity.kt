package com.example.rush.ui.restaurant.menu.confirmation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.model.Order
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.databinding.ConfirmationActivityBinding
import com.example.rush.ui.restaurant.RestaurantAdapter
import com.example.rush.utils.MyApp

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ConfirmationActivityBinding
    private lateinit var confirmationAdapter: ConfirmationAdapter
    private val orderRepository = RoomOrderDataSource()
    private val loginUser = MyApp.userPreferences.getUser()
    private val confirmationViewModel: ConfirmationViewModel by viewModels { ConfirmationViewModelFactory(orderRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfirmationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        confirmationAdapter = ConfirmationAdapter()

        binding.menuList.adapter = confirmationAdapter

        if (loginUser != null) setUp()

    }

    private fun setUp() {

        val order: Order? = intent.getParcelableExtra("order")

        if (order != null) {
            val menuList = order.menuList
            Log.d("order", menuList.toString())
            confirmationAdapter.submitList(order.menuList)
        }

    }

}