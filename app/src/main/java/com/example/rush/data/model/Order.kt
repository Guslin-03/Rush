package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Order(
    val id: Int?,
    val date: Date,
    val userId: String,
    val menuList: List<Menu>?
):Parcelable