package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantFilter (
    val originType: MutableList<String> = mutableListOf(),
    val specialty: MutableList<String> = mutableListOf(),
    var rating: Int = 0,
    var priceRange: Int = 0,
): Parcelable