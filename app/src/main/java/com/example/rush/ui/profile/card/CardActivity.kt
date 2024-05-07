package com.example.rush.ui.profile.card

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.databinding.CardActivityBinding
import com.example.rush.ui.order.OrderActivity
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class CardActivity : AppCompatActivity(){
    private lateinit var binding: CardActivityBinding
    private val userRepository = RoomUserDataSource()
    private val cardViewModel: CardViewModel by viewModels { CardViewModelFactory(userRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setIconSelected()

        binding.changeCreditCardButton.setOnClickListener{
            val user = MyApp.userPreferences.getUser()
            val cardNumberText = binding.creditCardNumber.text.toString()
            val cardNumber = if (cardNumberText.isNotEmpty()) cardNumberText.toLong() else null
            if (user != null && cardNumber != null && isValidCardNumber(cardNumberText)) {
                cardViewModel.onUpdateCard(user.id, cardNumber)
            } else {
                Toast.makeText(this, getString(R.string.numero_tarjeta_invalido), Toast.LENGTH_SHORT).show()
            }
        }
        cardViewModel.card.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    val user = MyApp.userPreferences.getUser()
                    val cardNumberText = binding.creditCardNumber.text.toString()
                    val cardNumber = if (cardNumberText.isNotEmpty()) cardNumberText.toLong() else null
                    if (user != null && cardNumber != null) {
                        user.cardNumber=cardNumber
                        MyApp.userPreferences.saveUser(user)
                        Toast.makeText(this, getString(R.string.actualizado), Toast.LENGTH_LONG).show()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                }

            }
        }
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
    private fun setData(){
        val user = MyApp.userPreferences.getUser()
        if (user != null) {
            binding.rushPointsValue.text = user.rushPoints.toString()
            binding.creditCardNumber.setText(user.cardNumber.toString())
        }

    }
    private fun isValidCardNumber(cardNumber: String): Boolean {
        val trimmedCardNumber = cardNumber.trim()
        return trimmedCardNumber.length in 15..19 && trimmedCardNumber.matches(Regex("\\d+")) // Verifica longitud y si son todos dígitos
    }
    private fun setIconSelected(){
        binding.bottomNavigation.menu.findItem(R.id.profile)?.isChecked = true
    }
}