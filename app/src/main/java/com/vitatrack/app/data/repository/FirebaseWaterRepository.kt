package com.vitatrack.app.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vitatrack.app.data.model.WaterIntake
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class FirebaseWaterRepository {
    
    private val firestore = FirebaseFirestore.getInstance()
    private val waterCollection = firestore.collection("water_intake")
    
    companion object {
        private const val TAG = "FirebaseWaterRepository"
    }
    
    /**
     * Add a new water intake entry to Firestore
     */
    suspend fun addWater(waterIntake: WaterIntake): Result<String> {
        return try {
            val documentRef = waterCollection.add(waterIntake).await()
            Log.d(TAG, "Water intake added with ID: ${documentRef.id}")
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding water intake", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get all water intake logs for a specific user
     */
    suspend fun getWaterLogs(userId: String): Result<List<WaterIntake>> {
        return try {
            val querySnapshot = waterCollection
                .whereEqualTo("userId", userId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val waterLogs = querySnapshot.documents.mapNotNull { document ->
                document.toObject(WaterIntake::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${waterLogs.size} water logs for user: $userId")
            Result.success(waterLogs)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting water logs", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get water intake logs for a specific user within a date range
     */
    suspend fun getWaterLogsByDateRange(
        userId: String, 
        startDate: Date, 
        endDate: Date
    ): Result<List<WaterIntake>> {
        return try {
            val querySnapshot = waterCollection
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val waterLogs = querySnapshot.documents.mapNotNull { document ->
                document.toObject(WaterIntake::class.java)?.copy(id = document.id)
            }
            
            Log.d(TAG, "Retrieved ${waterLogs.size} water logs for user: $userId in date range")
            Result.success(waterLogs)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting water logs by date range", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get today's water intake logs for a specific user
     */
    suspend fun getTodayWaterLogs(userId: String): Result<List<WaterIntake>> {
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
        
        return getWaterLogsByDateRange(userId, startOfDay.time, endOfDay.time)
    }
    
    /**
     * Update an existing water intake entry
     */
    suspend fun updateWater(waterIntake: WaterIntake): Result<Unit> {
        return try {
            if (waterIntake.id.isEmpty()) {
                return Result.failure(IllegalArgumentException("Water intake ID cannot be empty"))
            }
            
            waterCollection.document(waterIntake.id)
                .set(waterIntake.copy(updatedAt = Date()))
                .await()
            
            Log.d(TAG, "Water intake updated with ID: ${waterIntake.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating water intake", e)
            Result.failure(e)
        }
    }
    
    /**
     * Delete a water intake entry
     */
    suspend fun deleteWater(waterIntakeId: String): Result<Unit> {
        return try {
            if (waterIntakeId.isEmpty()) {
                return Result.failure(IllegalArgumentException("Water intake ID cannot be empty"))
            }
            
            waterCollection.document(waterIntakeId).delete().await()
            Log.d(TAG, "Water intake deleted with ID: $waterIntakeId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting water intake", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get total water intake for today for a user
     */
    suspend fun getTodayWaterIntake(userId: String): Result<Int> {
        return try {
            val todayWaterLogsResult = getTodayWaterLogs(userId)
            if (todayWaterLogsResult.isSuccess) {
                val totalWater = todayWaterLogsResult.getOrNull()?.sumOf { it.amountMl } ?: 0
                Result.success(totalWater)
            } else {
                Result.failure(todayWaterLogsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's water intake", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get water intake for a specific date range
     */
    suspend fun getWaterIntakeByDateRange(
        userId: String, 
        startDate: Date, 
        endDate: Date
    ): Result<Int> {
        return try {
            val waterLogsResult = getWaterLogsByDateRange(userId, startDate, endDate)
            if (waterLogsResult.isSuccess) {
                val totalWater = waterLogsResult.getOrNull()?.sumOf { it.amountMl } ?: 0
                Result.success(totalWater)
            } else {
                Result.failure(waterLogsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting water intake by date range", e)
            Result.failure(e)
        }
    }
    
    /**
     * Add quick water intake (common amounts)
     */
    suspend fun addQuickWater(userId: String, amountMl: Int, notes: String? = null): Result<String> {
        val waterIntake = WaterIntake(
            userId = userId,
            amountMl = amountMl,
            notes = notes,
            date = Date()
        )
        return addWater(waterIntake)
    }
}
