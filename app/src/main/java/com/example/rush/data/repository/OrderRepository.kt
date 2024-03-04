package com.example.rush.data.repository

import com.example.rush.data.model.Order
import com.example.rush.utils.Resource

interface OrderRepository {
    suspend fun getOrders(): Resource<List<Order>>
    suspend fun getOrdersByUser(userId: Int): Resource<List<Order>>
    suspend fun createOrder(order: Order): Resource<Order>
}