package com.example.rush.data.repository.menu

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rush.data.model.Menu
import com.example.rush.data.repository.MenuRepository
import com.example.rush.utils.MyApp
import com.example.rush.utils.Resource

class RoomMenuDataSource: MenuRepository {

    private val menuDAO: MenuDAO = MyApp.db.menuDAO()

    override suspend fun getMenus(): Resource<List<Menu>> {
        val response = menuDAO.getMenus().map { it.toMenu() }
        return Resource.success(response)
    }

    override suspend fun getMenusByIdRestaurant(): Resource<List<Menu>> {
        val response = menuDAO.getMenusByIdRestaurant().map { it.toMenu() }
        return Resource.success(response)
    }

    override suspend fun createMenu(menuArray: Array<Menu>): Resource<Void> {
        val response = menuDAO.createMenu(menuArray.map { it.toDbMenu()})
        if (response.isNotEmpty()) {
            return Resource.success()
        }
        return Resource.error("Ha sucedido un error")
    }

}

fun DbMenu.toMenu() = Menu(id, name, description, price, type, restaurantId)
fun Menu.toDbMenu() = DbMenu(id, name, description, price, type, restaurantId)

@Dao
interface MenuDAO {
    @Query("SELECT * FROM menus ORDER BY name")
    suspend fun getMenus() : List<DbMenu>
    @Query("SELECT * FROM menus ORDER BY name")
    suspend fun getMenusByIdRestaurant() : List<DbMenu>
    @Insert
    suspend fun createMenu(menuList: List<DbMenu>) : LongArray

}