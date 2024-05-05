package com.example.rush.ui.pay

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.rush.databinding.PayActivityBinding
import com.example.rush.ui.order.OrderActivity
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
        binding.btnPay.setOnClickListener {
            MyApp.userPreferences.saveActiveOrder(true)
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.editTextCardNumber.addTextChangedListener{
            checkFieldsForNewCard()
        }

        binding.editTextExpiryDate.addTextChangedListener{
            checkFieldsForNewCard()
        }

        binding.editTextCVV.addTextChangedListener{
            checkFieldsForNewCard()
        }
        binding.editTextExpiryDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // Formatear la fecha como MM/AA
                if (s?.length == 2 && s.toString().toInt() > 12) {
                    s.replace(0, 2, "12")
                } else if (s?.length == 4 && s[2] != '/') {
                    s.insert(2, "/")
                }
            }
        })
    }
    private fun checkFieldsForNewCard() {
        val cardNumber = binding.editTextCardNumber.text.toString()
        val expiryDate = binding.editTextExpiryDate.text.toString()
        val cvv = binding.editTextCVV.text.toString()

        val allFieldsCompleted = cardNumber.length == 16 && expiryDate.isNotEmpty() && cvv.length == 3

        binding.btnPay.isEnabled = allFieldsCompleted
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