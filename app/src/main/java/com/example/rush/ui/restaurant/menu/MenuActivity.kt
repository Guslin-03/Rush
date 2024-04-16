package com.example.rush.ui.restaurant.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.model.Menu
import com.example.rush.data.model.Order
import com.example.rush.data.model.Restaurant
import com.example.rush.data.repository.menu.MenuType
import com.example.rush.data.repository.menu.RoomMenuDataSource
import com.example.rush.databinding.MenuActivityBinding
import com.example.rush.databinding.SubitemMenuBinding
import com.example.rush.ui.restaurant.menu.adapter.DessertAdapter
import com.example.rush.ui.restaurant.menu.adapter.DrinksAdapter
import com.example.rush.ui.restaurant.menu.adapter.FirstsAdapter
import com.example.rush.ui.restaurant.menu.adapter.StartersAdapter
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource
import java.util.Date

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: MenuActivityBinding
    private lateinit var startersAdapter: StartersAdapter
    private lateinit var firstsAdapter: FirstsAdapter
    private lateinit var dessertAdapter: DessertAdapter
    private lateinit var drinksAdapter: DrinksAdapter
    private val menuRepository = RoomMenuDataSource()
    private lateinit var order: Order
    private var menuList = mutableListOf<Menu>()
    private val loginUser = MyApp.userPreferences.getUser()
    private val menuViewModel: MenuViewModel by viewModels { MenuViewModelFactory(menuRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Método de inicio
        if (loginUser != null) setUp()

        menuViewModel.menu.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    val startersList = mutableListOf<Menu>()
                    val firstsList = mutableListOf<Menu>()
                    val dessertsList = mutableListOf<Menu>()
                    val drinksList = mutableListOf<Menu>()
                    for (menu in it.data!!) {
                        if (menu.type == MenuType.STARTERS.toString()) startersList.add(menu)
                        if (menu.type == MenuType.FIRST.toString()) firstsList.add(menu)
                        if (menu.type == MenuType.DESSERT.toString()) dessertsList.add(menu)
                        if (menu.type == MenuType.DRINK.toString()) drinksList.add(menu)
                    }
                    startersAdapter.submitList(startersList)
                    firstsAdapter.submitList(firstsList)
                    dessertAdapter.submitList(dessertsList)
                    drinksAdapter.submitList(drinksList)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                }
            }
        }

        binding.confirmar.setOnClickListener {
            order = Order(null, Date(), loginUser!!.id, menuList)
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
            finish()
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

    private fun onMenuAddClickListener(menu: Menu, subItemMenuBinding: SubitemMenuBinding) {
        if (menu.id != null) {
            menu.amount += 1
            validateMenuHasAdded(menu)
            validateSomeMenuAdded()
            subItemMenuBinding.amountMenu.text = menu.amount.toString()
            subItemMenuBinding.subtractMenu.isEnabled = menu.amount != 0
        }
    }

    private fun onMenuSubtractClickListener(menu: Menu, subItemMenuBinding: SubitemMenuBinding) {
        if (menu.id != null) {
            if (menu.amount > 0) {
                menu.amount -= 1
                validateMenuHasRemoved(menu)
                validateSomeMenuAdded()
                subItemMenuBinding.amountMenu.text = menu.amount.toString()
                subItemMenuBinding.subtractMenu.isEnabled = menu.amount != 0
            }
        }
    }

    private fun validateMenuHasAdded(menu: Menu) {
        if (!menuList.contains(menu)) {
            menuList.add(menu)
        }
    }

    private fun validateMenuHasRemoved(menu: Menu) {
        if (menuList.contains(menu)) {
            val index = menuList.indexOfFirst { it.id == menu.id }
            if (menuList[index].amount == 0) {
                menuList.removeAt(index)
            }
        }
    }

    private fun validateSomeMenuAdded() {
        if (menuList.isEmpty()) {
            binding.confirmar.visibility = View.GONE
        }else {
            binding.confirmar.visibility = View.VISIBLE
        }
    }

    private fun setUp() {

        startersAdapter = StartersAdapter(
            ::onMenuAddClickListener,
            ::onMenuSubtractClickListener
        )
        firstsAdapter = FirstsAdapter(
            ::onMenuAddClickListener,
            ::onMenuSubtractClickListener
        )
        dessertAdapter = DessertAdapter(
            ::onMenuAddClickListener,
            ::onMenuSubtractClickListener
        )
        drinksAdapter = DrinksAdapter(
            ::onMenuAddClickListener,
            ::onMenuSubtractClickListener
        )
        disableRadioButtonsCulinaryStyle()

        binding.itemMenu.starters.adapter = startersAdapter
        binding.itemMenu.first.adapter = firstsAdapter
        binding.itemMenu.dessert.adapter = dessertAdapter
        binding.itemMenu.drink.adapter = drinksAdapter

        val selectedRestaurant: Restaurant? = intent.getParcelableExtra("selectedRestaurant")

        if (selectedRestaurant != null) {
            menuViewModel.onUpdateMenuList(selectedRestaurant.id)
        }

    }

}