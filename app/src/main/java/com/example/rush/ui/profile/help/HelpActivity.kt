package com.example.rush.ui.profile.help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.HelpActivityBinding

class HelpActivity : AppCompatActivity(){
    private lateinit var binding: HelpActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}