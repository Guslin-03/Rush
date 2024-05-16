package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmationData (
    val restaurantName: String,
    val totalAmount: Double
):Parcelable