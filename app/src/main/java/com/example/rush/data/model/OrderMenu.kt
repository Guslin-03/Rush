package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderMenu (
    val orderId: Int,
    val menuId: Int,
    val amount: Int
):Parcelable