package com.example.rush.data.model

import java.io.Serializable

data class ConfirmationData (
    val restaurantName: String,
    val totalAmount: Double
):Serializable