package com.example.rush.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Order(
    var id: Int?,
    val date: Date,
    val userId: Int,
    var menuList: List<Menu>
):Parcelable