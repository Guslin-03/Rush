package com.example.rush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.rush.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.postDelayed({ logIn() }, 1000)
    }

private fun logIn() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
}
}