package com.example.iitrace

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    const val USER_TOKEN = "user_token"

    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }

    fun saveUserData(context: Context, key: String, valueData: String) {
        saveString(context, key, valueData)
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun getIdData(context: Context): String? {
        return getString(context, "id")
    }

    fun getEmailData(context: Context): String? {
        return getString(context, "email")
    }

    fun getUsernameData(context: Context): String? {
        return getString(context, "username")
    }

    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun clearData(context: Context){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }
}