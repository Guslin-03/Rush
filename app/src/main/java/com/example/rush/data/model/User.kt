package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: Int?,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val phoneNumber: Int,
    val rushPoints: Int,
    val cardNumber: Long,
    val firstLogin: Boolean
): Parcelable