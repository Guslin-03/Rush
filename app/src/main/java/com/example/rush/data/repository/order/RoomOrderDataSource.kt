package com.example.rush.data.repository.order

import androidx.room.Dao
import androidx.room.Query
import com.example.rush.data.model.Order
import com.example.rush.data.repository.OrderRepository
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RoomOrderDataSource: OrderRepository {

    private val orderDao: OrderDAO = MyApp.db.orderDAO()

    override suspend fun getOrders(): Resource<List<Order>> {
        val response = orderDao.getOrders().map { it.toOrder() }
        return Resource.success(response)
    }

}

fun DbOrder.toOrder() = Order(id, date, userId, null)
fun Order.toDbOrder() = DbOrder(id, date, userId)

@Dao
interface OrderDAO {
    @Query("SELECT * FROM orders ORDER BY date")
    suspend fun getOrders() : List<DbOrder>

}