package com.example.rush.ui.restaurant.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.menu.RoomMenuDataSource
import com.example.rush.databinding.MenuActivityBinding
import com.example.rush.utils.Resource

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: MenuActivityBinding
    private lateinit var menuAdapter: MenuAdapter
    private val menuRepository = RoomMenuDataSource()
    private val menuViewModel: MenuViewModel by viewModels { MenuViewModelFactory(menuRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuAdapter = MenuAdapter()
        disableRadioButtonsCulinaryStyle()

        binding.itemMenu.starters.adapter = menuAdapter

        val selectedRestaurant: Restaurant? = intent.getParcelableExtra("selectedRestaurant")

        if (selectedRestaurant != null) {
            menuViewModel.onUpdateMenuList(selectedRestaurant.id)
        }

        menuViewModel.menu.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    menuAdapter.submitList(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                }
            }
        }

    }

    private fun disableRadioButtonsCulinaryStyle() {
        val recyclerView = mutableMapOf<Int, Int>()
        for (i in 0 until binding.itemMenu.itemMenu.childCount) {
            if (binding.itemMenu.itemMenu.getChildAt(i) is Button) {
                val button: Button = binding.itemMenu.itemMenu.getChildAt(i) as Button
                button.setOnClickListener {
                    if (button.text == "Entrantes") {
                        val visibility = recyclerView[binding.itemMenu.starters.id] ?: binding.itemMenu.starters.visibility
                        if (visibility == View.VISIBLE) {
                            binding.itemMenu.starters.visibility = View.GONE
                            recyclerView[binding.itemMenu.starters.id] = View.GONE
                        } else {
                            binding.itemMenu.starters.visibility = View.VISIBLE
                            recyclerView[binding.itemMenu.starters.id] = View.VISIBLE
                        }
                    }
                    if (button.text == "Primeros") {
                        val visibility = recyclerView[binding.itemMenu.first.id] ?: binding.itemMenu.first.visibility
                        if (visibility == View.VISIBLE) {
                            binding.itemMenu.first.visibility = View.GONE
                            recyclerView[binding.itemMenu.first.id] = View.GONE
                        } else {
                            binding.itemMenu.first.visibility = View.VISIBLE
                            recyclerView[binding.itemMenu.first.id] = View.VISIBLE
                        }
                    }
                    if (button.text == "Postres") {
                        val visibility = recyclerView[binding.itemMenu.dessert.id] ?: binding.itemMenu.dessert.visibility
                        if (visibility == View.VISIBLE) {
                            binding.itemMenu.dessert.visibility = View.GONE
                            recyclerView[binding.itemMenu.dessert.id] = View.GONE
                        } else {
                            binding.itemMenu.dessert.visibility = View.VISIBLE
                            recyclerView[binding.itemMenu.dessert.id] = View.VISIBLE
                        }
                    }
                    if (button.text == "Bebidas") {
                        val visibility = recyclerView[binding.itemMenu.drink.id] ?: binding.itemMenu.drink.visibility
                        if (visibility == View.VISIBLE) {
                            binding.itemMenu.drink.visibility = View.GONE
                            recyclerView[binding.itemMenu.drink.id] = View.GONE
                        } else {
                            binding.itemMenu.drink.visibility = View.VISIBLE
                            recyclerView[binding.itemMenu.drink.id] = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

}