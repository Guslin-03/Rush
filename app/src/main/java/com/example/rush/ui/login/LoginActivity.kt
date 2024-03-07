package com.example.rush.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.repository.user.RoomUserDataSource
import com.example.rush.databinding.LoginActivityBinding
import com.example.rush.utils.Resource
import com.example.rush.ui.welcome.WelcomeActivity
import com.example.rush.utils.MyApp

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val userRepository = RoomUserDataSource()
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModelFactory(userRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        previousLoginState()

        loginViewModel.login.observe(this) {
            Log.d("LOGIN", ""+it.status)
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val user = it.data
                    if (user != null) {
                        MyApp.userPreferences.saveRememberMeState(binding.rememberMe.isChecked)
                        MyApp.userPreferences.saveUser(user)
                        loginSuccess()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, "Los datos introducidos son incorrectos", Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                }
            }
        }

        binding.login.setOnClickListener {
            tryToLogin()
            }
        }
    private fun tryToLogin(){
        loginViewModel.onLogin(binding.email.text.toString(), binding.password.text.toString())
    }
private fun previousLoginState(){
    val userLogged=MyApp.userPreferences.getUser()

    binding.rememberMe.isChecked= MyApp.userPreferences.getRememberMeState()
    if(binding.rememberMe.isChecked){
        binding.email.setText(userLogged?.email)
        binding.password.setText(userLogged?.password)
        tryToLogin()
    }else {
        MyApp.userPreferences.removeData()
    }
}
    private fun loginSuccess(){
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
