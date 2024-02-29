package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant (
    val id: Int?,
    val name: String,
    val location: String
): Parcelable