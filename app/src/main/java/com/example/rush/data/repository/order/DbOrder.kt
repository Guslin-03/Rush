package com.example.rush.data.repository.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rush.data.repository.restaurant.DbRestaurant
import java.util.Date

@Entity(tableName = "orders",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = DbRestaurant::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION
        )]
)
class DbOrder (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "userId") val userId: Int
)