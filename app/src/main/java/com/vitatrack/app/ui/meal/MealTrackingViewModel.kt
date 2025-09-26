package com.vitatrack.app.ui.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.model.Meal
import com.vitatrack.app.data.model.MealType
import kotlinx.coroutines.launch
import java.util.*

class MealTrackingViewModel(private val mealDao: MealDao) : ViewModel() {

    private val _todayMeals = MutableLiveData<List<Meal>>()
    val todayMeals: LiveData<List<Meal>> = _todayMeals

    private val _dailyCalories = MutableLiveData<Int>()
    val dailyCalories: LiveData<Int> = _dailyCalories

    private val _dailyProtein = MutableLiveData<Float>()
    val dailyProtein: LiveData<Float> = _dailyProtein

    private val _dailyCarbs = MutableLiveData<Float>()
    val dailyCarbs: LiveData<Float> = _dailyCarbs

    private val _dailyFat = MutableLiveData<Float>()
    val dailyFat: LiveData<Float> = _dailyFat

    fun loadTodayMeals(userId: String) {
        viewModelScope.launch {
            val today = Calendar.getInstance()
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)
            val startOfDay = today.time

            today.add(Calendar.DAY_OF_MONTH, 1)
            val endOfDay = today.time

            // Load meals
            val meals = mealDao.getMealsByDateRange(userId, startOfDay, endOfDay)
            meals.observeForever { mealList ->
                _todayMeals.postValue(mealList)
                calculateNutrition(mealList)
            }
        }
    }

    private fun calculateNutrition(meals: List<Meal>) {
        val totalCalories = meals.sumOf { it.calories }
        val totalProtein = meals.sumOf { it.protein?.toDouble() ?: 0.0 }.toFloat()
        val totalCarbs = meals.sumOf { it.carbs?.toDouble() ?: 0.0 }.toFloat()
        val totalFat = meals.sumOf { it.fat?.toDouble() ?: 0.0 }.toFloat()

        _dailyCalories.postValue(totalCalories)
        _dailyProtein.postValue(totalProtein)
        _dailyCarbs.postValue(totalCarbs)
        _dailyFat.postValue(totalFat)
    }

    fun addMeal(
        userId: String,
        name: String,
        type: MealType,
        calories: Int,
        protein: Float? = null,
        carbs: Float? = null,
        fat: Float? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            val meal = Meal(
                userId = userId,
                name = name,
                type = type,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat,
                notes = notes,
                date = Date()
            )
            mealDao.insertMeal(meal)
            loadTodayMeals(userId) // Refresh data
        }
    }

    fun updateMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.updateMeal(meal)
            loadTodayMeals(meal.userId) // Refresh data
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.deleteMeal(meal)
            loadTodayMeals(meal.userId) // Refresh data
        }
    }

    fun getCalorieProgress(goal: Int): Int {
        val current = _dailyCalories.value ?: 0
        return ((current.toFloat() / goal.toFloat()) * 100).toInt().coerceAtMost(100)
    }
}
