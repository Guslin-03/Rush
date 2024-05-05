package com.example.rush.ui.order

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.databinding.OrderActivityBinding
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: OrderActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val order= MyApp.userPreferences.getActiveOrder()
        if (order){
            binding.texto.text= getString(R.string.preparando)
            binding.countdownTimer.visibility = View.VISIBLE
            binding.cooking.visibility = View.VISIBLE
            binding.kitchen.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.recoger.visibility = View.GONE

            object : CountDownTimer(10000, 1000) { // 10 segundos con actualización cada segundo
                override fun onTick(millisUntilFinished: Long) {
                    binding.countdownTimer.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    binding.countdownTimer.visibility = View.GONE
                    binding.cooking.visibility = View.GONE
                    binding.texto.text= getString(R.string.recoger_pedido)
                    binding.button.visibility = View.VISIBLE
                    binding.recoger.visibility = View.VISIBLE
                }
            }.start()
        }else{
            binding.texto.text= getString(R.string.sin_pedido)
            binding.cooking.visibility = View.GONE
            binding.countdownTimer.visibility = View.GONE
            binding.kitchen.visibility=View.VISIBLE
            binding.button.visibility = View.GONE
            binding.recoger.visibility = View.GONE
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

                R.id.profile-> {
                    showProfile()
                    true
                }

                else -> false // Manejo predeterminado para otros elementos
            }

        }
        binding.button.setOnClickListener {
            MyApp.userPreferences.saveActiveOrder(false)
            Toast.makeText(this, getString(R.string.aproveche), Toast.LENGTH_LONG).show()
            showStart()
        }
        onBackPressedDispatcher.addCallback(this) {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.salir_app))
        builder.setMessage(getString(R.string.seguro_salir))
        builder.setPositiveButton(R.string.si) { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        builder.setNegativeButton(R.string.no, null)
        builder.show()
    }
    private fun showStart(){
        val intent = Intent(this, RestaurantActivity::class.java)
        startActivity(intent)
        finish()
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

}