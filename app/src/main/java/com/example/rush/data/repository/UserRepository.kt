package com.example.rush.data.repository

import com.example.rush.data.model.User
import com.example.rush.utils.Resource

interface UserRepository {
    suspend fun getUsers(): Resource<List<User>>
    suspend fun getUserById(userId: Int): Resource<User>
    suspend fun login(username: String, password: String): Resource<Void>
    suspend fun createUser(userArray: Array<User>): Resource<Void>
}