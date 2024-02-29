package com.example.rush.data.repository.user

import androidx.room.Dao
import androidx.room.Query
import com.example.rush.data.model.User
import com.example.rush.data.repository.UserRepository
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RoomUserDataSource: UserRepository {

    private val userDao: UserDAO = MyApp.db.userDAO()

    override suspend fun getUsers(): Resource<List<User>> {
        val response = userDao.getUsers().map { it.toUser() }
        return Resource.success(response)
    }


}

fun DbUser.toUser() = User(id, name, surname, email, phoneNumber, rushPoints, cardNumber, firstLogin)
fun User.toDbUser() = DbUser(id, name, surname, email, phoneNumber, rushPoints, cardNumber, firstLogin)

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getUsers() : List<DbUser>

}