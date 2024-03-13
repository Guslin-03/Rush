package com.example.rush.data.repository.restaurant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
class DbRestaurant (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "originType") val originType: String,
    @ColumnInfo(name = "specialty") val specialty: String,
    @ColumnInfo(name = "numberReviews") val numberReviews: Int,
    @ColumnInfo(name = "priceRange") val priceRange: Int
)