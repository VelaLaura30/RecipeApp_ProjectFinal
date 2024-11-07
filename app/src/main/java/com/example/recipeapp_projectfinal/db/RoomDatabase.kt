package com.example.recipeapp_projectfinal.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
}
