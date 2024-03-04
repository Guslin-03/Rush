package com.example.rush.data.repository.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class DbUser(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: Int,
    @ColumnInfo(name = "rushPoints") val rushPoints: Int,
    @ColumnInfo(name = "cardNumber") val cardNumber: Long,
    @ColumnInfo(name = "firstLogin") val firstLogin: Boolean
)