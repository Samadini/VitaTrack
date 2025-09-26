package com.vitatrack.app.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vitatrack.app.data.model.Exercise
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class ExerciseRepository {
    
    private val firestore = FirebaseFirestore.getInstance()
    private val exercisesCollection = firestore.collection("exercises")
    
    companion object {
        private const val TAG = "ExerciseRepository"
    }
    
    /**
     * Add a new exercise to Firestore
     */
    suspend fun addExercise(exercise: Exercise): Result<String> {
        return try {
            val documentRef = exercisesCollection.add(exercise).await()
            Log.d(TAG, "Exercise added with ID: ${documentRef.id}")
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding exercise", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get all exercises for a specific user
     */
    suspend fun getExercises(userId: String): Result<List<Exercise>> {
        return try {
            val querySnapshot = exercisesCollection
                .whereEqualTo("userId", userId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val exercises = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Exercise::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${exercises.size} exercises for user: $userId")
            Result.success(exercises)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting exercises", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get exercises for a specific user within a date range
     */
    suspend fun getExercisesByDateRange(
        userId: String, 
        startDate: Date, 
        endDate: Date
    ): Result<List<Exercise>> {
        return try {
            val querySnapshot = exercisesCollection
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val exercises = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Exercise::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${exercises.size} exercises for user: $userId in date range")
            Result.success(exercises)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting exercises by date range", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get today's exercises for a specific user
     */
    suspend fun getTodayExercises(userId: String): Result<List<Exercise>> {
        val calendar = Calendar.getInstance()
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
        
        return getExercisesByDateRange(userId, startOfDay.time, endOfDay.time)
    }
    
    /**
     * Update an existing exercise
     */
    suspend fun updateExercise(exercise: Exercise): Result<Unit> {
        return try {
            if (exercise.id.isEmpty()) {
                return Result.failure(IllegalArgumentException("Exercise ID cannot be empty"))
            }
            
            exercisesCollection.document(exercise.id)
                .set(exercise.copy(updatedAt = Date()))
                .await()
            
            Log.d(TAG, "Exercise updated with ID: ${exercise.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating exercise", e)
            Result.failure(e)
        }
    }
    
    /**
     * Delete an exercise
     */
    suspend fun deleteExercise(exerciseId: String): Result<Unit> {
        return try {
            if (exerciseId.isEmpty()) {
                return Result.failure(IllegalArgumentException("Exercise ID cannot be empty"))
            }
            
            exercisesCollection.document(exerciseId).delete().await()
            Log.d(TAG, "Exercise deleted with ID: $exerciseId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting exercise", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total calories burned today for a user
     */
    suspend fun getTodayCaloriesBurned(userId: String): Result<Int> {
        return try {
            val todayExercisesResult = getTodayExercises(userId)
            if (todayExercisesResult.isSuccess) {
                val totalCalories = todayExercisesResult.getOrNull()?.sumOf { it.caloriesBurned } ?: 0
                Result.success(totalCalories)
            } else {
                Result.failure(todayExercisesResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's calories burned", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total exercise duration today for a user
     */
    suspend fun getTodayExerciseDuration(userId: String): Result<Int> {
        return try {
            val todayExercisesResult = getTodayExercises(userId)
            if (todayExercisesResult.isSuccess) {
                val totalDuration = todayExercisesResult.getOrNull()?.sumOf { it.duration } ?: 0
                Result.success(totalDuration)
            } else {
                Result.failure(todayExercisesResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's exercise duration", e)
            Result.failure(e)
        }
    }
}
