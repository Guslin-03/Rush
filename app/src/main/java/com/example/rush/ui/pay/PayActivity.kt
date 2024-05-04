package com.example.rush.ui.pay

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.databinding.PayActivityBinding
import com.example.rush.utils.MyApp


class PayActivity : AppCompatActivity() {
    private lateinit var binding: PayActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PayActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()

        binding.spinnerSavedCards.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCard = parent?.getItemAtPosition(position).toString()
                    if (selectedCard == getString(com.example.rush.R.string.otra_tarjeta)) {
                        binding.layoutNewCard.visibility = View.VISIBLE
                        binding.btnPay.isEnabled = false;
                    } else {
                        binding.layoutNewCard.visibility = View.GONE
                        binding.btnPay.isEnabled = true;
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }
    private fun setData(){
        val user = MyApp.userPreferences.getUser()
        if (user != null) {
            val cardNumbers = mutableListOf<String>()
            cardNumbers.add(user.cardNumber.toString())
            cardNumbers.add(getString(com.example.rush.R.string.otra_tarjeta))
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, cardNumbers)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerSavedCards.adapter = adapter
            binding.spinnerSavedCards.setSelection(0)
        }

    }

}