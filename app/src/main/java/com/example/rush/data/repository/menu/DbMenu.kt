package com.example.rush.data.repository.menu

import androidx.room.ColumnInfo
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.rush.data.repository.restaurant.DbRestaurant

@Entity(tableName = "menus",
    foreignKeys = [
    androidx.room.ForeignKey(
        entity = DbRestaurant::class,
        parentColumns = ["id"],
        childColumns = ["restaurantId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class DbMenu (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "restaurantId") val restaurantId: Int,
)