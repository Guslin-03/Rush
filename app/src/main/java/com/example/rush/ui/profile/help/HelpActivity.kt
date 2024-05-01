package com.example.rush.ui.profile.help

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.databinding.HelpActivityBinding
import com.example.rush.ui.order.OrderActivity
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.ui.restaurant.RestaurantActivity

class HelpActivity : AppCompatActivity(){
    private lateinit var binding: HelpActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.orders -> {
                    showOrders()
                    true
                }

                R.id.start -> {
                    showStart()
                    true
                }

                R.id.profile -> {
                    showProfile()
                    true
                }

                else -> false // Manejo predeterminado para otros elementos
            }
        }
    }
    private fun showStart(){
        val intent = Intent(this, RestaurantActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showProfile(){
        finish()
    }
    private fun showOrders(){
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
        finish()
    }
}