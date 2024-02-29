package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu (
    val id: Int?,
    val name: String,
    val description: String,
    val price: Float,
    val type: String,
    val restaurantId: Int
):Parcelable