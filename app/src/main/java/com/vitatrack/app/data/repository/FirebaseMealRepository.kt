package com.vitatrack.app.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vitatrack.app.data.model.Meal
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class FirebaseMealRepository {
    
    private val firestore = FirebaseFirestore.getInstance()
    private val mealsCollection = firestore.collection("meals")
    
    companion object {
        private const val TAG = "FirebaseMealRepository"
    }
    
    /**
     * Add a new meal to Firestore
     */
    suspend fun addMeal(meal: Meal): Result<String> {
        return try {
            val documentRef = mealsCollection.add(meal).await()
            Log.d(TAG, "Meal added with ID: ${documentRef.id}")
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding meal", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get all meals for a specific user
     */
    suspend fun getMeals(userId: String): Result<List<Meal>> {
        return try {
            val querySnapshot = mealsCollection
                .whereEqualTo("userId", userId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val meals = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Meal::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${meals.size} meals for user: $userId")
            Result.success(meals)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting meals", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get meals for a specific user within a date range
     */
    suspend fun getMealsByDateRange(
        userId: String, 
        startDate: Date, 
        endDate: Date
    ): Result<List<Meal>> {
        return try {
            val querySnapshot = mealsCollection
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val meals = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Meal::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${meals.size} meals for user: $userId in date range")
            Result.success(meals)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting meals by date range", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get today's meals for a specific user
     */
    suspend fun getTodayMeals(userId: String): Result<List<Meal>> {
        val startOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endOfDay = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        
        return getMealsByDateRange(userId, startOfDay.time, endOfDay.time)
    }
    
    /**
     * Update an existing meal
     */
    suspend fun updateMeal(meal: Meal): Result<Unit> {
        return try {
            if (meal.id.isEmpty()) {
                return Result.failure(IllegalArgumentException("Meal ID cannot be empty"))
            }
            
            mealsCollection.document(meal.id)
                .set(meal.copy(updatedAt = Date()))
                .await()
            
            Log.d(TAG, "Meal updated with ID: ${meal.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating meal", e)
            Result.failure(e)
        }
    }
    
    /**
     * Delete a meal
     */
    suspend fun deleteMeal(mealId: String): Result<Unit> {
        return try {
            if (mealId.isEmpty()) {
                return Result.failure(IllegalArgumentException("Meal ID cannot be empty"))
            }
            
            mealsCollection.document(mealId).delete().await()
            Log.d(TAG, "Meal deleted with ID: $mealId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting meal", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total calories consumed today for a user
     */
    suspend fun getTodayCalories(userId: String): Result<Int> {
        return try {
            val todayMealsResult = getTodayMeals(userId)
            if (todayMealsResult.isSuccess) {
                val totalCalories = todayMealsResult.getOrNull()?.sumOf { it.calories } ?: 0
                Result.success(totalCalories)
            } else {
                Result.failure(todayMealsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's calories", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total protein consumed today for a user
     */
    suspend fun getTodayProtein(userId: String): Result<Float> {
        return try {
            val todayMealsResult = getTodayMeals(userId)
            if (todayMealsResult.isSuccess) {
                val totalProtein = todayMealsResult.getOrNull()?.sumOf { it.protein?.toDouble() ?: 0.0 }?.toFloat() ?: 0f
                Result.success(totalProtein)
            } else {
                Result.failure(todayMealsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's protein", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total carbs consumed today for a user
     */
    suspend fun getTodayCarbs(userId: String): Result<Float> {
        return try {
            val todayMealsResult = getTodayMeals(userId)
            if (todayMealsResult.isSuccess) {
                val totalCarbs = todayMealsResult.getOrNull()?.sumOf { it.carbs?.toDouble() ?: 0.0 }?.toFloat() ?: 0f
                Result.success(totalCarbs)
            } else {
                Result.failure(todayMealsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's carbs", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total fat consumed today for a user
     */
    suspend fun getTodayFat(userId: String): Result<Float> {
        return try {
            val todayMealsResult = getTodayMeals(userId)
            if (todayMealsResult.isSuccess) {
                val totalFat = todayMealsResult.getOrNull()?.sumOf { it.fat?.toDouble() ?: 0.0 }?.toFloat() ?: 0f
                Result.success(totalFat)
            } else {
                Result.failure(todayMealsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's fat", e)
            Result.failure(e)
        }
    }
}
