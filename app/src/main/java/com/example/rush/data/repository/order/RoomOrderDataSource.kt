package com.example.rush.data.repository.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rush.data.model.Order
import com.example.rush.data.model.OrderMenu
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
            if (order.id != null) {
                order.menuList = orderMenuDAO.getMenusByOrders(order.id!!).map { it.toMenu() }
            }
        }
        return Resource.success(response)
    }

    override suspend fun createOrder(order: Order): Resource<Order> {
        val response = orderDAO.createOrder(order.toDbOrder()).toInt()
        if (response != 0) {
            order.id = response
            val menuList = order.menuList
            for (menu in menuList) {
                if (menu.id != null) {
                    orderMenuDAO.createOrderMenu(OrderMenu(order.id!!, menu.id, menu.amount).toDbOrderMenu())
                }
            }
            return Resource.success(order)
        }
        return Resource.error("Ha sucedido un error")
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
    suspend fun createOrder(order: DbOrder) : Long

}

fun OrderMenu.toDbOrderMenu() = DbOrderMenu(orderId, menuId, amount)

@Dao
interface OrderMenuDAO {
    @Query("SELECT * FROM menus " +
            "JOIN order_menu ON menus.id = order_menu.menuId " +
            "WHERE orderId = :orderId ORDER BY menuId")
    suspend fun getMenusByOrders(orderId: Int) : List<DbMenu>

    @Insert
    suspend fun createOrderMenu(menuOrder: DbOrderMenu) : Long

}