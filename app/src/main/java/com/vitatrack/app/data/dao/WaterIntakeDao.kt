package com.vitatrack.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vitatrack.app.data.model.WaterIntake
import java.util.Date

@Dao
interface WaterIntakeDao {
    
    @Query("SELECT * FROM water_intake WHERE userId = :userId ORDER BY date DESC")
    fun getWaterIntakeByUserId(userId: String): LiveData<List<WaterIntake>>
    
    @Query("SELECT * FROM water_intake WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getWaterIntakeByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<WaterIntake>>
    
    @Query("SELECT * FROM water_intake WHERE id = :id")
    suspend fun getWaterIntakeById(id: String): WaterIntake?
    
    @Query("SELECT SUM(amountMl) FROM water_intake WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    suspend fun getTotalWaterIntakeByDateRange(userId: String, startDate: Date, endDate: Date): Int?
    
    @Insert
    suspend fun insertWaterIntake(waterIntake: WaterIntake)
    
    @Update
    suspend fun updateWaterIntake(waterIntake: WaterIntake)
    
    @Delete
    suspend fun deleteWaterIntake(waterIntake: WaterIntake)
    
    @Query("DELETE FROM water_intake WHERE id = :id")
    suspend fun deleteWaterIntakeById(id: String)
}
