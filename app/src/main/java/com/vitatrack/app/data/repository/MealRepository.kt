package com.vitatrack.app.data.repository

import androidx.lifecycle.LiveData
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.model.Meal
import java.util.Date

class MealRepository(private val mealDao: MealDao) {
    
    fun getMealsByUserId(userId: String): LiveData<List<Meal>> {
        return mealDao.getMealsByUserId(userId)
    }
    
    fun getMealsByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Meal>> {
        return mealDao.getMealsByDateRange(userId, startDate, endDate)
    }
    
    suspend fun getMealById(id: Long): Meal? {
        return mealDao.getMealById(id)
    }
    
    suspend fun getTotalCaloriesByDateRange(userId: String, startDate: Date, endDate: Date): Int {
        return mealDao.getTotalCaloriesByDateRange(userId, startDate, endDate) ?: 0
    }
    
    suspend fun getTotalProteinByDateRange(userId: String, startDate: Date, endDate: Date): Float {
        return mealDao.getTotalProteinByDateRange(userId, startDate, endDate) ?: 0f
    }
    
    suspend fun getTotalCarbsByDateRange(userId: String, startDate: Date, endDate: Date): Float {
        return mealDao.getTotalCarbsByDateRange(userId, startDate, endDate) ?: 0f
    }
    
    suspend fun getTotalFatByDateRange(userId: String, startDate: Date, endDate: Date): Float {
        return mealDao.getTotalFatByDateRange(userId, startDate, endDate) ?: 0f
    }
    
    suspend fun insertMeal(meal: Meal): Long {
        return mealDao.insertMeal(meal)
    }
    
    suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal)
    }
    
    suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }
    
    suspend fun deleteMealById(id: Long) {
        mealDao.deleteMealById(id)
    }
}
