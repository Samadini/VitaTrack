package com.vitatrack.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vitatrack.app.data.model.Meal
import java.util.Date

@Dao
interface MealDao {
    
    @Query("SELECT * FROM meals WHERE userId = :userId ORDER BY date DESC")
    fun getMealsByUserId(userId: String): LiveData<List<Meal>>
    
    @Query("SELECT * FROM meals WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getMealsByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Meal>>
    
    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealById(id: String): Meal?
    
    @Query("SELECT SUM(calories) FROM meals WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalCaloriesByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT SUM(protein) FROM meals WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalProteinByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT SUM(carbs) FROM meals WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalCarbsByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Query("SELECT SUM(fat) FROM meals WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalFatByDateRange(userId: String, startDate: Date, endDate: Date): Float?
    
    @Insert
    suspend fun insertMeal(meal: Meal)
    
    @Update
    suspend fun updateMeal(meal: Meal)
    
    @Delete
    suspend fun deleteMeal(meal: Meal)
    
    @Query("DELETE FROM meals WHERE id = :id")
    suspend fun deleteMealById(id: String)
}
