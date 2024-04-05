package com.example.rush.ui.profile

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.rush.R
import com.example.rush.databinding.CardActivityBinding
import com.example.rush.databinding.ProfileActivityBinding
import com.example.rush.ui.profile.card.CardActivity
import com.example.rush.ui.profile.help.HelpActivity
import com.example.rush.ui.profile.info.InfoActivity
import com.example.rush.ui.profile.password.PasswordActivity
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ProfileActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView: RecyclerView = findViewById(R.id.options_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProfileAdapter { option ->
            when (option) {
                "Mis datos" -> {
                    showInfo()
                }

                "Cambiar contraseña" -> {
                    showPass()
                }

                "Pedidos anteriores" -> {
                    // Manejar acción para "Pedidos anteriores"
                }

                "Métodos de pago" -> {
                    showCard()
                }

                "Idioma" -> {
                    // Manejar acción para "Idioma"
                }

                "Ayuda" -> {
                    showHelp()
                }

            }
        }
        recyclerView.adapter = adapter

        setIcon()
        val user = MyApp.userPreferences.getUser()
        if (user != null) {
            setData()
            showPhoto()
        }

        binding.darkMode.setOnClickListener {
            val currentNightMode =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.orders -> {
                    //FUNCIONALIDAD ORDERS
                    true
                }

                R.id.start -> {
                    showStart()
                    true
                }

                R.id.profile -> {
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
    private fun showInfo(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showCard(){
        val intent = Intent(this, CardActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showHelp(){
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showPass(){
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun setData() {
        val user = MyApp.userPreferences.getUser()
        if (user != null) {
            val greeting = getString(R.string.hi, user.name, user.surname)
            binding.greetingText.text = greeting
        }
    }

    private fun showPhoto() {
        val cameraPhoto = MyApp.userPreferences.getProfilePictureUriCamera()
        val savedPhotoLocal = MyApp.userPreferences.getProfilePictureUri()
        if (cameraPhoto != null) {
            val photo = Uri.parse(cameraPhoto.toString())
            Glide.with(this)
                .load(photo)
                .apply(RequestOptions().transform(CircleCrop()))
                .into(binding.profilePicture)
        } else if (savedPhotoLocal != null) {
            Glide.with(this)
                .load(savedPhotoLocal)
                .apply(RequestOptions().transform(CircleCrop()))
                .into(binding.profilePicture)
        }
    }

    private fun setIcon() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.darkMode.setImageResource(R.drawable.moon)
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                binding.darkMode.setImageResource(R.drawable.sun)
            }
        }
    }
}
