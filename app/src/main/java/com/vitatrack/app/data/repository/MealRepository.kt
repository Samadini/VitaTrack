package com.vitatrack.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.dao.FoodItemDao
import com.vitatrack.app.data.model.Meal
import com.vitatrack.app.data.model.FoodItem
import com.vitatrack.app.data.model.MealWithFoodItems
import java.util.Date

class MealRepository(
    private val mealDao: MealDao,
    private val foodItemDao: FoodItemDao
) {
    
    fun getMealsByUserId(userId: String): LiveData<List<Meal>> {
        return mealDao.getMealsByUserId(userId)
    }
    
    fun getMealsByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Meal>> {
        return mealDao.getMealsByDateRange(userId, startDate, endDate)
    }
    
    suspend fun getMealById(id: String): Meal? {
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
    
    suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal)
    }
    
    suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal)
    }
    
    suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }
    
    suspend fun deleteMealById(id: String) {
        // Delete associated food items first
        foodItemDao.deleteFoodItemsByMealId(id)
        mealDao.deleteMealById(id)
    }
    
    // FoodItem operations
    fun getFoodItemsByMealId(mealId: String): LiveData<List<FoodItem>> {
        return foodItemDao.getFoodItemsByMealId(mealId)
    }
    
    suspend fun getFoodItemsByMealIdSync(mealId: String): List<FoodItem> {
        return foodItemDao.getFoodItemsByMealIdSync(mealId)
    }
    
    suspend fun getFoodItemById(id: String): FoodItem? {
        return foodItemDao.getFoodItemById(id)
    }
    
    suspend fun insertFoodItem(foodItem: FoodItem) {
        foodItemDao.insertFoodItem(foodItem)
    }
    
    suspend fun insertFoodItems(foodItems: List<FoodItem>) {
        foodItemDao.insertFoodItems(foodItems)
    }
    
    suspend fun updateFoodItem(foodItem: FoodItem) {
        foodItemDao.updateFoodItem(foodItem)
    }
    
    suspend fun deleteFoodItem(foodItem: FoodItem) {
        foodItemDao.deleteFoodItem(foodItem)
    }
    
    suspend fun deleteFoodItemById(id: String) {
        foodItemDao.deleteFoodItemById(id)
    }
    
    // Combined operations
    suspend fun getMealWithFoodItems(mealId: String): MealWithFoodItems? {
        val meal = getMealById(mealId) ?: return null
        val foodItems = getFoodItemsByMealIdSync(mealId)
        return MealWithFoodItems(meal, foodItems)
    }
    
    fun getMealsWithFoodItemsByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<MealWithFoodItems>> {
        val result = MediatorLiveData<List<MealWithFoodItems>>()
        val mealsLiveData = getMealsByDateRange(userId, startDate, endDate)
        
        result.addSource(mealsLiveData) { meals ->
            // This is a simplified version - in a real app you might want to optimize this
            result.value = meals.map { meal ->
                // Note: This won't be reactive to food item changes
                // For full reactivity, you'd need a more complex implementation
                MealWithFoodItems(meal, emptyList())
            }
        }
        
        return result
    }
}
