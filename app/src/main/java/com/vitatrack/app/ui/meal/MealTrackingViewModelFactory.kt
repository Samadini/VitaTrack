package com.vitatrack.app.ui.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitatrack.app.data.dao.MealDao

class MealTrackingViewModelFactory(
    private val mealDao: MealDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealTrackingViewModel::class.java)) {
            return MealTrackingViewModel(mealDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
