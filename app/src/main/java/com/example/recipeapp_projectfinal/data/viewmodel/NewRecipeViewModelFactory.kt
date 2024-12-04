package com.example.recipeapp_projectfinal.data.viewmodel

import NewRecipeViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp_projectfinal.db.NewRecipeDao

class NewRecipeViewModelFactory(private val newRecipeDao: NewRecipeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewRecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewRecipeViewModel(newRecipeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
