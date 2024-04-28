package com.example.rush.ui.profile.password

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.R
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.databinding.PasswordActivityBinding
import com.example.rush.ui.order.OrderActivity
import com.example.rush.ui.profile.ProfileActivity
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class PasswordActivity : AppCompatActivity() {
    private lateinit var binding: PasswordActivityBinding
    private val userRepository = RoomUserDataSource()
    private val passwordViewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(
            userRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changePassword.setOnClickListener {
            if(checkData()){
                val user=MyApp.userPreferences.getUser()
                if (user != null) {
                    val newPass=binding.newPassword1.text.toString()
                    val oldPass=binding.currentPassword.text.toString()
                    Log.d("PASS", ""+oldPass)
                    passwordViewModel.onUpdatePass(user.id!!, newPass, oldPass)
                }

            }
        }
        passwordViewModel.pass.observe(this) {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    val user = MyApp.userPreferences.getUser()
                    val pass = binding.newPassword1.text.toString()
                    if (user != null) {
                        user.password=pass
                        MyApp.userPreferences.saveUser(user)
                        Toast.makeText(this, "Se ha actualizado correctamente", Toast.LENGTH_LONG).show()
                    }
                    clearData()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    clearData()
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
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showOrders(){
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun clearData(){
        binding.currentPassword.setText("")
        binding.newPassword1.setText("")
        binding.newPassword2.setText("")
    }
    private fun checkData(): Boolean {
        val currentPassword = binding.currentPassword.text.toString()
        val newPassword1 = binding.newPassword1.text.toString()
        val newPassword2 = binding.newPassword2.text.toString()

        if (currentPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()) {
            Toast.makeText(this, R.string.toast_empty_1, Toast.LENGTH_LONG).show()
            binding.currentPasswordLayout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
            binding.newPassword1Layout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
            binding.newPassword2Layout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)

            return false
        }

        if (currentPassword != newPassword1) {
            if (newPassword1 == newPassword2) {
                if (newPassword1.length >= 8) {
                    return true
                } else {
                    Toast.makeText(this, R.string.toast_password_lenght, Toast.LENGTH_LONG).show()
                    binding.newPassword1.setTextColor(Color.RED)
                    binding.newPassword2.setTextColor(Color.BLACK)

                    binding.newPassword1Layout.defaultHintTextColor =
                        ColorStateList.valueOf(Color.BLACK)
                    binding.newPassword2Layout.defaultHintTextColor =
                        ColorStateList.valueOf(Color.BLACK)
                    return false
                }
            } else {
                Toast.makeText(this, R.string.toast_password_matches, Toast.LENGTH_LONG).show()
                binding.newPassword1.setTextColor(Color.RED)
                binding.newPassword2.setTextColor(Color.RED)

                binding.newPassword1Layout.defaultHintTextColor =
                    ColorStateList.valueOf(Color.BLACK)
                binding.newPassword2Layout.defaultHintTextColor =
                    ColorStateList.valueOf(Color.BLACK)
                return false
            }

        } else {
            Toast.makeText(this, R.string.toast_password_old_new_matches, Toast.LENGTH_LONG).show()
            binding.currentPasswordLayout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
            binding.newPassword1Layout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
            binding.newPassword2Layout.defaultHintTextColor = ColorStateList.valueOf(Color.RED)
            return false
        }

    }
}