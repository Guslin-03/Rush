package com.example.rush.data.repository.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.rush.data.repository.menu.DbMenu

@Entity(tableName = "order_menu",
    primaryKeys = ["orderId", "menuId"],
    foreignKeys = [
        ForeignKey(
            entity = DbOrder::class,
            parentColumns = ["id"],
            childColumns = ["orderId"]
        ),
        ForeignKey(
            entity = DbMenu::class,
            parentColumns = ["id"],
            childColumns = ["menuId"]
        )
    ])
class DbOrderMenu (
    val orderId: Int,
    val menuId: Int,
    @ColumnInfo(name = "amount") val amount: Int
)