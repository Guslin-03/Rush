package com.example.rush.data.repository.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rush.data.model.User
import com.example.rush.data.repository.UserRepository
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource
import java.util.Date

class RoomUserDataSource: UserRepository {

    private val userDao: UserDAO = MyApp.db.userDAO()

    override suspend fun getUsers(): Resource<List<User>> {
        val response = userDao.getUsers().map { it.toUser() }
        return Resource.success(response)
    }

    override suspend fun getUserById(userId: Int): Resource<User> {
        val response = userDao.getUserById(userId).toUser()
        return Resource.success(response)
    }

    override suspend fun login(username: String, password: String): Resource<User> {
        val dbUser = userDao.login(username, password)
        return if (dbUser!=null){ //TODO QUITAR ESTE WARNING
            val response = dbUser.toUser()
            Resource.success(response)
        }else{
            Resource.error("Los datos introducidos son incorrectos")
        }

    }

    override suspend fun createUser(userArray: Array<User>): Resource<Void> {
        val response = userDao.createUser(userArray.map {it.toDbUser()})
        if (response.isNotEmpty()) {
            return Resource.success()
        }
        return Resource.error("Ha sucedido un error")
    }
    override suspend fun updateCard(id:Int, cardNumber:Long): Resource<Void> {
        val response = userDao.updateCard(id, cardNumber)
        return if (response == 1) {
            Resource.success()
        } else {
            Resource.error("Se ha producido un error al actualizar la tarjeta")
        }
    }
    override suspend fun updatePass(id:Int, newPass:String, oldPass:String): Resource<Void> {
        val response = userDao.updatePass(id, newPass, oldPass)
        return if (response == 1) {
            Resource.success()
        } else {
            Resource.error("Se ha producido un error al actualizar la contraseña")
        }
    }

}

fun DbUser.toUser() = User(id, name, surname, email, "12341234", phoneNumber, rushPoints, cardNumber, firstLogin)
fun User.toDbUser() = DbUser(id, name, surname, email, "12341234", phoneNumber, rushPoints, cardNumber, firstLogin)

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getUsers() : List<DbUser>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int) : DbUser

    @Insert
    suspend fun createUser(userArray: List<DbUser>) : LongArray

    @Query("SELECT * FROM users WHERE email = :username AND password = :password")
    suspend fun login(username: String, password: String) : DbUser

    @Query("UPDATE users SET cardNumber = :cardNumber WHERE id = :id")
    suspend fun updateCard(id: Int?, cardNumber: Long?) : Int

    @Query("UPDATE users SET password = :newPass WHERE id = :id and password= :oldPass")
    suspend fun updatePass(id: Int?, newPass: String, oldPass: String) : Int
}