package com.vitatrack.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vitatrack.app.data.model.Exercise
import java.util.Date

@Dao
interface ExerciseDao {
    
    @Query("SELECT * FROM exercises WHERE userId = :userId ORDER BY date DESC")
    fun getExercisesByUserId(userId: String): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getExercisesByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Exercise>>
    
    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getExerciseById(id: Long): Exercise?
    
    @Query("SELECT SUM(caloriesBurned) FROM exercises WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalCaloriesBurnedByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Query("SELECT SUM(duration) FROM exercises WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalDurationByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Insert
    suspend fun insertExercise(exercise: Exercise): Long
    
    @Update
    suspend fun updateExercise(exercise: Exercise)
    
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
    
    @Query("DELETE FROM exercises WHERE id = :id")
    suspend fun deleteExerciseById(id: Long)
}
