package com.example.rush.data.repository

import com.example.rush.data.model.Menu
import com.example.rush.utils.Resource

interface MenuRepository {
    suspend fun getMenus(): Resource<List<Menu>>
}