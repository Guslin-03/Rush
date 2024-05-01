package com.example.rush

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import com.example.rush.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.postDelayed({ logIn() }, 1000)
        onBackPressedDispatcher.addCallback(this) {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Salir de la aplicación")
        builder.setMessage("¿Estás seguro de que quieres salir?")
        builder.setPositiveButton("Sí") { dialogInterface: DialogInterface, i: Int ->
            // Finaliza la actividad actual (es decir, cierra la aplicación)
            finish()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }


    private fun logIn() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}