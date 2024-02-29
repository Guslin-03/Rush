package com.example.rush.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.rush.data.repository.MyAppRoomDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MyApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var userPreferences: UserPreferences
        lateinit var db: MyAppRoomDataBase
        lateinit var appScope: CoroutineScope
    }
    override fun onCreate(){
        super.onCreate()
        context = this
        userPreferences = UserPreferences()
        appScope = CoroutineScope(Dispatchers.IO)
        isDatabaseExists(context)
        db = Room
            .databaseBuilder(this, MyAppRoomDataBase::class.java, "rush-db")
            .build()
    }

    private fun isDatabaseExists(context: Context) {
        val dbFile = context.getDatabasePath("rush-db")

        if(!dbFile.exists()) {
            userPreferences.saveDataBaseIsCreated(false)
        } else {
            userPreferences.saveDataBaseIsCreated(true)
        }

    }

}