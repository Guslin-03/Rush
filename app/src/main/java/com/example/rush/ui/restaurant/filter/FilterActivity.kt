package com.example.rush.ui.restaurant.filter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.FilterActivityBinding
import com.example.rush.ui.restaurant.RestaurantActivity

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: FilterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirm.setOnClickListener {
            goToRestaurants()
        }

    }

    private fun goToRestaurants(){
        val intent = Intent(this, RestaurantActivity::class.java)
        startActivity(intent)
        finish()
    }

}