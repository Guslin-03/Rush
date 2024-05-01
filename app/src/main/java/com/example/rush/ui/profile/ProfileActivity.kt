package com.example.rush.ui.profile

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.rush.R
import com.example.rush.databinding.ProfileActivityBinding
import com.example.rush.ui.login.LoginActivity
import com.example.rush.ui.order.OrderActivity
import com.example.rush.ui.profile.card.CardActivity
import com.example.rush.ui.profile.help.HelpActivity
import com.example.rush.ui.profile.info.InfoActivity
import com.example.rush.ui.profile.password.PasswordActivity
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp
import java.util.Locale

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
                getString(R.string.mis_datos) -> {
                    showInfo()
                }

                getString(R.string.cambiar_contraseña) -> {
                    showPass()
                }

                getString(R.string.metodos_de_pago) -> {
                    showCard()
                }

                getString(R.string.idioma) -> {
                    showLanguageDialog()
                }

                getString(R.string.ayuda) -> {
                    showHelp()
                }

                getString(R.string.cerrar_sesion) -> {
                    logout()
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
                    showOrders()
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
        onBackPressedDispatcher.addCallback(this) {
            showExitConfirmationDialog()
        }
    }
    private fun logout() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.salir_app))
        builder.setMessage(getString(R.string.seguro_cerrar))
        builder.setPositiveButton(R.string.si) { dialogInterface: DialogInterface, i: Int ->
            MyApp.userPreferences.removeData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(R.string.no, null)
        builder.show()
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
    private fun showOrders(){
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showInfo(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }
    private fun showCard(){
        val intent = Intent(this, CardActivity::class.java)
        startActivity(intent)
    }
    private fun showHelp(){
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }
    private fun showPass(){
        val intent = Intent(this, PasswordActivity::class.java)
        startActivity(intent)
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
    private fun showLanguageDialog() {
        val languages = getLanguageOptions()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_language))
            .setItems(languages) { _, i ->
                val locale = when (i) {
                    0 -> Locale("es")
                    1 -> Locale("en")
                    2 -> Locale("eu")
                    else -> Locale.getDefault()
                }
                setAppLocale(locale)
            }
            .show()
    }

    private fun getLanguageOptions(): Array<String> {
        return arrayOf(
            getString(R.string.es),
            getString(R.string.en),
            getString(R.string.eu)
        )
    }


    private fun setAppLocale(locale: Locale) {
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Reiniciar la actividad para aplicar los cambios de idioma
        recreate()
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
