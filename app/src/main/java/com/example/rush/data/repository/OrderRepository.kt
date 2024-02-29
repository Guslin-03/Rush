package com.example.rush.data.repository

import com.example.rush.data.model.Order
import com.example.rush.utils.Resource

interface OrderRepository {
    suspend fun getOrders(): Resource<List<Order>>
}