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
import com.example.rush.data.model.RestaurantFilter
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.databinding.RestaurantActivityBinding
import com.example.rush.ui.order.OrderActivity
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.ui.restaurant.filter.FilterActivity
import com.example.rush.ui.restaurant.menu.MenuActivity
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

        binding.complexFilter.setOnClickListener {
            goToFilter()
        }

        restaurantViewModel.restaurant.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    restaurantAdapter.submitList(it.data)
                    advanceFilter()
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
                    showOrders()
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

        }

    }

    private fun advanceFilter() {
        val restaurantFilter: RestaurantFilter? = intent.getParcelableExtra("restaurantFilter")

        if (restaurantFilter != null) {
            var currentRestaurantList = restaurantAdapter.currentList

            currentRestaurantList = currentRestaurantList.filter { it.priceRange > restaurantFilter.priceRange }
            currentRestaurantList = currentRestaurantList.filter { it.rating > restaurantFilter.rating }

            if (restaurantFilter.originType.isNotEmpty()) {
                currentRestaurantList = currentRestaurantList.filter { restaurant ->
                    restaurantFilter.originType.contains(restaurant.originType) }
            }

            if (restaurantFilter.specialty.isNotEmpty()) {
                currentRestaurantList = currentRestaurantList.filter { restaurant ->
                    restaurantFilter.specialty.contains(restaurant.specialty) }
            }
            restaurantAdapter.submitList(currentRestaurantList)
        }
    }

    private fun showProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showOrders(){
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToFilter(){
        val intent = Intent(this, FilterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMenu(restaurant: Restaurant){
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("selectedRestaurant", restaurant)
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
            goToMenu(restaurant)
        }
    }

}