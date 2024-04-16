package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int?,
    val name: String,
    val description: String,
    val price: Double,
    val type: String,
    val restaurantId: Int,
    var amount : Int
):Parcelable {

    constructor(
        id: Int?,
        name: String,
        description: String,
        price: Double,
        type: String,
        restaurantId: Int
    ) : this (
        id = id,
        name = name,
        description = description,
        price = price,
        type = type,
        restaurantId = restaurantId,
        amount = 0
    )

}