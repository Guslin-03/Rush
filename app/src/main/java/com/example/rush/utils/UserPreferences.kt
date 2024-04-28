package com.example.rush.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.example.rush.data.model.User
import com.google.gson.Gson

class UserPreferences {
    private val sharedPreferences: SharedPreferences by lazy {
        MyApp.context.getSharedPreferences(MyApp.context.packageName, Context.MODE_PRIVATE)
    }

    companion object {
        const val USER_INFO = "user_info"
        const val REMEMBER_ME = "remember_me"
        const val PASS = "pass"
        const val DATABASE_CREATED = "isDatabaseCreated"
        const val PROFILE_PICTURE = "profile_picture"
        const val PROFILE_PICTURE_CAMERA = "profile_picture_camera"
        const val ACTIVE_ORDER = "active_order"
    }

    fun saveProfilePicture(uri: Uri) {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_PICTURE, uri.toString())
        editor.apply()
    }

    fun getProfilePictureUri(): Uri? {
        val uriString = sharedPreferences.getString(PROFILE_PICTURE, null)
        return uriString?.let { Uri.parse(it) }
    }

    fun saveProfilePictureCamera(uri: Uri) {
        val editor = sharedPreferences.edit()
        editor.putString(PROFILE_PICTURE_CAMERA, uri.toString())
        editor.apply()
    }

    fun getProfilePictureUriCamera(): Uri? {
        val uriString = sharedPreferences.getString(PROFILE_PICTURE_CAMERA, null)
        return uriString?.let { Uri.parse(it) }
    }

    fun removeProfilePictureUri() {
        val editor = sharedPreferences.edit()
        editor.remove("profile_picture")
        editor.apply()
    }

    fun removeProfilePictureUriCamera() {
        val editor = sharedPreferences.edit()
        editor.remove("profile_picture_camera")
        editor.apply()
    }


    fun saveDataBaseIsCreated(isDatabaseCreated: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(DATABASE_CREATED, isDatabaseCreated)
        editor.apply()
    }

    fun getSaveDataBaseIsCreated(): Boolean {
        return sharedPreferences.getBoolean(DATABASE_CREATED, false)
    }

    fun saveUser(loginUser: User) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val userJson = gson.toJson(loginUser)
        editor.putString(USER_INFO, userJson)
        editor.apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences.getString(USER_INFO, null)
        if (userJson != null) {
            val gson = Gson()
            return gson.fromJson(userJson, User::class.java)
        }
        return null
    }

    fun saveRememberMeState(rememberMe: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(REMEMBER_ME, rememberMe)
        editor.apply()
    }

    fun getRememberMeState(): Boolean {
        return sharedPreferences.getBoolean(REMEMBER_ME, false)
    }

    fun saveActiveOrder(activeOrder: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(ACTIVE_ORDER, activeOrder)
        editor.apply()
    }

    fun getActiveOrder(): Boolean {
        return sharedPreferences.getBoolean(ACTIVE_ORDER, false)
    }

    fun removeData() {
        val editor = sharedPreferences.edit()
        editor.remove("user_info")
        editor.putBoolean(REMEMBER_ME, false)
        editor.apply()
    }

    fun removePicture() {
        val editor = sharedPreferences.edit()
        editor.remove("profile_picture_camera")
        editor.remove("profile_picture")
        editor.apply()
    }

    fun savePass(pass: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PASS, pass)
        editor.apply()
    }

    fun getPass(): String? {
        return sharedPreferences.getString(PASS, null)
    }
}