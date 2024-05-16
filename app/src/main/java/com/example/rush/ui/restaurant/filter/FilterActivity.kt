package com.example.rush.ui.restaurant.filter

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.rush.data.model.RestaurantFilter
import com.example.rush.databinding.FilterActivityBinding
import com.example.rush.ui.restaurant.RestaurantActivity
import java.util.Locale


class FilterActivity : AppCompatActivity() {

    private lateinit var binding: FilterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableRadioButtonsCulinaryStyle()
        disableRadioButtonsSpecialty()

        binding.cancel.setOnClickListener {
            goToRestaurants(RestaurantFilter())
        }

        binding.confirm.setOnClickListener {
            val restaurantFilter = setRestaurantFilter()
            goToRestaurants(restaurantFilter)
        }

    }

    private fun disableRadioButtonsCulinaryStyle() {
        val radioButtonStates = mutableMapOf<Int, Boolean>()
        for (i in 0 until binding.culinaryStyleRG.childCount) {
            if (binding.culinaryStyleRG.getChildAt(i) is LinearLayout) {
                val linearLayout: LinearLayout = binding.culinaryStyleRG.getChildAt(i) as LinearLayout
                for (j in 0 until linearLayout.childCount) {
                    if (linearLayout.getChildAt(j) is RadioButton) {
                        val radioButton: RadioButton = linearLayout.getChildAt(j) as RadioButton
                        radioButton.setOnClickListener {
                            val wasPreviouslySelected = radioButtonStates[radioButton.id] ?: false
                            if (wasPreviouslySelected) {
                                radioButton.isChecked = false
                                radioButtonStates[radioButton.id] = false
                            } else {
                                radioButtonStates[radioButton.id] = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun disableRadioButtonsSpecialty() {
        val radioButtonStates = mutableMapOf<Int, Boolean>()
        for (i in 0 until binding.specialtyRG.childCount) {
            if (binding.specialtyRG.getChildAt(i) is LinearLayout) {
                val linearLayout: LinearLayout = binding.specialtyRG.getChildAt(i) as LinearLayout
                for (j in 0 until linearLayout.childCount) {
                    if (linearLayout.getChildAt(j) is RadioButton) {
                        val radioButton: RadioButton = linearLayout.getChildAt(j) as RadioButton
                        radioButton.setOnClickListener {
                            val wasPreviouslySelected = radioButtonStates[radioButton.id] ?: false
                            if (wasPreviouslySelected) {
                                radioButton.isChecked = false
                                radioButtonStates[radioButton.id] = false
                            } else {
                                radioButtonStates[radioButton.id] = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setRestaurantFilter() : RestaurantFilter{

        val restaurantFilter = RestaurantFilter()

        setCulinaryStyle(restaurantFilter)
        setSpecialty(restaurantFilter)
        setRatingPriceRange(restaurantFilter)

        return restaurantFilter

    }

    private fun setCulinaryStyle(restaurantFilter: RestaurantFilter){
        for (i in 0 until binding.culinaryStyleRG.childCount) {
            if (binding.culinaryStyleRG.getChildAt(i) is LinearLayout) {
                val linearLayout: LinearLayout = binding.culinaryStyleRG.getChildAt(i) as LinearLayout
                for (j in 0 until linearLayout.childCount) {
                    if (linearLayout.getChildAt(j) is RadioButton) {
                        val radioButton: RadioButton = linearLayout.getChildAt(j) as RadioButton
                        if (radioButton.isChecked) {
                            restaurantFilter.originType.add(
                                radioButton.text.toString().uppercase(
                                    Locale.ROOT
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setSpecialty(restaurantFilter: RestaurantFilter){
        for (i in 0 until binding.specialtyRG.childCount) {
            if (binding.specialtyRG.getChildAt(i) is LinearLayout) {
                val linearLayout: LinearLayout = binding.specialtyRG.getChildAt(i) as LinearLayout
                for (j in 0 until linearLayout.childCount) {
                    if (linearLayout.getChildAt(j) is RadioButton) {
                        val radioButton: RadioButton = linearLayout.getChildAt(j) as RadioButton
                        if (radioButton.isChecked) {
                            restaurantFilter.specialty.add(
                                radioButton.text.toString().uppercase(
                                    Locale.ROOT
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setRatingPriceRange(restaurantFilter: RestaurantFilter){
        restaurantFilter.rating = binding.seekBarRating.progress + 1
        restaurantFilter.priceRange = binding.seekBarPriceRange.progress + 1
    }

    private fun goToRestaurants(restaurantFilter: RestaurantFilter){
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra("restaurantFilter", restaurantFilter)
        startActivity(intent)
        finish()
    }

}