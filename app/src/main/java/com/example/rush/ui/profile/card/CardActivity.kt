package com.example.rush.ui.profile.card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.CardActivityBinding

class CardActivity : AppCompatActivity(){
    private lateinit var binding: CardActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}