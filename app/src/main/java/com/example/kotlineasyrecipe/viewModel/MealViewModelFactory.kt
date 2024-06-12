package com.example.kotlineasyrecipe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlineasyrecipe.db.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailModel(mealDatabase) as T
    }
}