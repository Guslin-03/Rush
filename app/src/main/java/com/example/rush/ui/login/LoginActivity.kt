package com.example.rush.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.databinding.LoginActivityBinding
import com.example.rush.ui.welcome.WelcomeActivity
import com.example.rush.utils.Resource

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val userRepository = RoomUserDataSource()
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory(userRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.login.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loginSuccess()
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                }
            }
        }

        binding.login.setOnClickListener {
                loginViewModel.onLogin(binding.email.text.toString(), binding.password.text.toString())
            }
        }

    private fun loginSuccess(){
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
