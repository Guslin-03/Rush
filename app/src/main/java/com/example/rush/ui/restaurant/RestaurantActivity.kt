package com.example.rush.ui.restaurant

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.databinding.RestaurantActivityBinding
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: RestaurantActivityBinding
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val restaurantRepository = RoomRestaurantDataSource()
    private val restaurantViewModel: RestaurantViewModel by viewModels { RestaurantViewModelFactory(restaurantRepository) }
    private val loginUser = MyApp.userPreferences.getUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RestaurantActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantAdapter = RestaurantAdapter(
            ::onRestaurantClickListener
        )

        binding.restaurantList.adapter = restaurantAdapter

        restaurantViewModel.restaurant.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    restaurantAdapter.submitList(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                }

            }
        }

        binding.filterText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterByText(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.orders -> {
                    //FUNCIONALIDAD ORDERS
                    true
                }

                R.id.start -> {
                    true
                }

                R.id.profile-> {
                    showProfile()
                    true
                }

                else -> false // Manejo predeterminado para otros elementos
            }

        }}
    private fun showProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun filterByText(s: CharSequence?){
        val searchText = s.toString().trim()
        var originalList = restaurantViewModel.restaurant.value?.data
        if (originalList == null) originalList = emptyList()

        val filteredList = originalList.filter { restaurant ->
            restaurant.name.contains(searchText, ignoreCase = true)
        }

        restaurantAdapter.submitList(filteredList)
    }

    private fun onRestaurantClickListener(restaurant: Restaurant) {
        if (loginUser != null && restaurant.id != null) {
            //TODO cuando se hace click en el restaurante llevar a la siguiente pantalla que es la de
            //los menus del usuario
        }

    }

}