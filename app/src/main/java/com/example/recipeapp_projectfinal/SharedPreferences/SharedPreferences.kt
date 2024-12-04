package com.example.recipeapp_projectfinal.SharedPreferences

import android.content.Context

class SharedPreferences {

    fun saveUserName(context: Context, userName: String) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_name", userName)
        editor.apply()
    }


}