package com.vitatrack.app.data.repository

import androidx.lifecycle.LiveData
import com.vitatrack.app.data.dao.ExerciseDao
import com.vitatrack.app.data.model.Exercise
import java.util.Date

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    
    fun getExercisesByUserId(userId: String): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByUserId(userId)
    }
    
    fun getExercisesByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByDateRange(userId, startDate, endDate)
    }
    
    suspend fun getExerciseById(id: Long): Exercise? {
        return exerciseDao.getExerciseById(id)
    }
    
    suspend fun getTotalCaloriesBurnedByDateRange(userId: String, startDate: Date, endDate: Date): Int {
        return exerciseDao.getTotalCaloriesBurnedByDateRange(userId, startDate, endDate) ?: 0
    }
    
    suspend fun getTotalDurationByDateRange(userId: String, startDate: Date, endDate: Date): Int {
        return exerciseDao.getTotalDurationByDateRange(userId, startDate, endDate) ?: 0
    }
    
    suspend fun insertExercise(exercise: Exercise): Long {
        return exerciseDao.insertExercise(exercise)
    }
    
    suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }
    
    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }
    
    suspend fun deleteExerciseById(id: Long) {
        exerciseDao.deleteExerciseById(id)
    }
}
