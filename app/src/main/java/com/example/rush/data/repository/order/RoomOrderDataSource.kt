package com.example.rush.data.repository.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rush.data.model.Order
import com.example.rush.data.repository.OrderRepository
import com.example.rush.data.repository.menu.DbMenu
import com.example.rush.data.repository.menu.toMenu
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RoomOrderDataSource: OrderRepository {

    private val orderDAO: OrderDAO = MyApp.db.orderDAO()
    private val orderMenuDAO: OrderMenuDAO = MyApp.db.orderMenuDAO()

    override suspend fun getOrders(): Resource<List<Order>> {
        val response = orderDAO.getOrders().map { it.toOrder() }
        return Resource.success(response)
    }

    override suspend fun getOrdersByUser(userId: Int): Resource<List<Order>> {
        val response = orderDAO.getOrdersByUser(userId).map { it.toOrder() }
        for (order in response) {
            order.menuList = orderMenuDAO.getMenusByOrders(order.id).map { it.toMenu() }
        }
        return Resource.success(response)
    }

    override suspend fun createOrder(newOrder: Order): Resource<Order> {
        newOrder.id = orderDAO.createOrder(newOrder.toDbOrder()).toInt()
        return Resource.success(newOrder)
    }

}

fun DbOrder.toOrder() = Order(id, date, userId, emptyList())
fun Order.toDbOrder() = DbOrder(id, date, userId)

@Dao
interface OrderDAO {
    @Query("SELECT * FROM orders ORDER BY date")
    suspend fun getOrders() : List<DbOrder>

    @Query("SELECT * FROM orders WHERE id = :userId ORDER BY date")
    suspend fun getOrdersByUser(userId: Int) : List<DbOrder>

    @Insert
    suspend fun createOrder(newOrder: DbOrder) : Long

}

@Dao
interface OrderMenuDAO {
    @Query("SELECT * FROM menus " +
            "JOIN order_menu ON menus.id = order_menu.menuId " +
            "WHERE orderId = :orderId ORDER BY menuId")
    suspend fun getMenusByOrders(orderId: Int) : List<DbMenu>

}