package com.example.rush.ui.restaurant.menu.confirmation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.data.model.ConfirmationData
import com.example.rush.data.model.Order
import com.example.rush.data.repository.order.RoomOrderDataSource
import com.example.rush.data.repository.restaurant.RoomRestaurantDataSource
import com.example.rush.databinding.ConfirmationActivityBinding
import com.example.rush.ui.pay.PayActivity
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ConfirmationActivityBinding
    private lateinit var confirmationAdapter: ConfirmationAdapter
    private val orderRepository = RoomOrderDataSource()
    private val restaurantRepository = RoomRestaurantDataSource()
    private val loginUser = MyApp.userPreferences.getUser()
    private val confirmationViewModel: ConfirmationViewModel by viewModels { ConfirmationViewModelFactory(orderRepository, restaurantRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfirmationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        confirmationAdapter = ConfirmationAdapter()

        binding.menuList.adapter = confirmationAdapter

        if (loginUser != null) setUp()

        binding.confirmar.setOnClickListener {
            val confirmationData = ConfirmationData(
                binding.title.text.toString().split(" ")[2],
                binding.totalAmount.text.toString().toDouble())
            goToPay(confirmationData)
            finish()
        }

        binding.cancelar.setOnClickListener {
            finish()
        }

        confirmationViewModel.restaurant.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    val title = getString(R.string.tlt_confirm_activity) + (it.data?.name ?: "" )
                    binding.title.text = title
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                }

            }
        }

    }

    private fun setUp() {
        Log.d("DEBUG", "HOLA");
        val order: Order? = intent.getParcelableExtra("order")

        if (order != null) {

            confirmationViewModel.onGetRestaurantById(order.menuList[0].restaurantId)

            val itemAmountMap = mutableMapOf<String, Int>() // Mapa para mantener un seguimiento de la cantidad de cada artículo

            // Iterar sobre los elementos del menú y actualizar el mapa de cantidad
            order.menuList.forEach { menuItem ->
                val existingAmount = itemAmountMap[menuItem.name] ?: 0
                itemAmountMap[menuItem.name] = existingAmount + menuItem.amount
            }

            // Calcular el precio total
            var totalPrice = 0.0
            itemAmountMap.forEach { (itemName, amount) ->
                val menuItem = order.menuList.find { it.name == itemName }
                if (menuItem != null) {
                    totalPrice += menuItem.price * amount
                }
            }

            binding.totalAmount.text = totalPrice.toString()
            confirmationAdapter.submitList(order.menuList)
        }

    }

    private fun goToPay(confirmationData: ConfirmationData){
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra("confirmationData", confirmationData)
        startActivity(intent)
    }

}