package com.example.rush.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.WelcomeActivityBinding
class WelcomeActivity  : AppCompatActivity(){
    private lateinit var binding: WelcomeActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}