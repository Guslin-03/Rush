package com.example.rush.data.repository

import com.example.rush.data.model.User
import com.example.rush.utils.Resource

interface UserRepository {
    suspend fun getUsers(): Resource<List<User>>
    suspend fun getUserById(userId: Int): Resource<User>
    suspend fun login(username: String, password: String): Resource<User>

    suspend fun createUser(userArray: Array<User>): Resource<Void>
    suspend fun updateCard(id:Int, cardNumber:Long): Resource<Void>
    suspend fun updatePass(id:Int, newPass:String, oldPass:String): Resource<Void>
}