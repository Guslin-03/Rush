package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant (
    val id: Int?,
    val name: String,
    val originType: String,
    val specialty: String,
    val rating: Float,
    val numberReviews: Int,
    val priceRange: Int,
    val location: String,
): Parcelable