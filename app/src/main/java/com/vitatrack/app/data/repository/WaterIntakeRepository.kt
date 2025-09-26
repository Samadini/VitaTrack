package com.vitatrack.app.data.repository

import androidx.lifecycle.LiveData
import com.vitatrack.app.data.dao.WaterIntakeDao
import com.vitatrack.app.data.model.WaterIntake
import java.util.Date

class WaterIntakeRepository(private val waterIntakeDao: WaterIntakeDao) {
    
    fun getWaterIntakeByUserId(userId: String): LiveData<List<WaterIntake>> {
        return waterIntakeDao.getWaterIntakeByUserId(userId)
    }
    
    fun getWaterIntakeByDateRange(userId: String, startDate: Date, endDate: Date): LiveData<List<WaterIntake>> {
        return waterIntakeDao.getWaterIntakeByDateRange(userId, startDate, endDate)
    }
    
    suspend fun getWaterIntakeById(id: String): WaterIntake? {
        return waterIntakeDao.getWaterIntakeById(id)
    }
    
    suspend fun getTotalWaterIntakeByDateRange(userId: String, startDate: Date, endDate: Date): Int {
        return waterIntakeDao.getTotalWaterIntakeByDateRange(userId, startDate, endDate) ?: 0
    }
    
    suspend fun insertWaterIntake(waterIntake: WaterIntake) {
        waterIntakeDao.insertWaterIntake(waterIntake)
    }
    
    suspend fun updateWaterIntake(waterIntake: WaterIntake) {
        waterIntakeDao.updateWaterIntake(waterIntake)
    }
    
    suspend fun deleteWaterIntake(waterIntake: WaterIntake) {
        waterIntakeDao.deleteWaterIntake(waterIntake)
    }
    
    suspend fun deleteWaterIntakeById(id: String) {
        waterIntakeDao.deleteWaterIntakeById(id)
    }
}
