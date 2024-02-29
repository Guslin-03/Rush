package com.example.rush.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.LoginActivityBinding

private lateinit var binding: LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}