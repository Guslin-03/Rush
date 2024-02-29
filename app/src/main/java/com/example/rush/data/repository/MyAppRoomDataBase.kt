package com.example.rush.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rush.data.repository.menu.DbMenu
import com.example.rush.data.repository.menu.MenuDAO
import com.example.rush.data.repository.order.DbOrder
import com.example.rush.data.repository.order.DbOrderMenu
import com.example.rush.data.repository.order.OrderDAO
import com.example.rush.data.repository.restaurant.DbRestaurant
import com.example.rush.data.repository.restaurant.RestaurantDAO
import com.example.rush.data.repository.user.DbUser
import com.example.rush.data.repository.user.UserDAO

@Database(
    entities = [
        DbUser::class,
        DbRestaurant::class,
        DbMenu::class,
        DbOrderMenu::class,
        DbOrder::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyAppRoomDataBase: RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun restaurantDAO(): RestaurantDAO
    abstract fun orderDAO(): OrderDAO
    abstract fun menuDAO(): MenuDAO

}
