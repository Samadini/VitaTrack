package com.vitatrack.app.ui.water

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitatrack.app.data.dao.WaterIntakeDao
import com.vitatrack.app.data.model.WaterIntake
import kotlinx.coroutines.launch
import java.util.*

class WaterTrackingViewModel(private val waterIntakeDao: WaterIntakeDao) : ViewModel() {

    private val _dailyWaterIntake = MutableLiveData<Int>()
    val dailyWaterIntake: LiveData<Int> = _dailyWaterIntake

    private val _waterLogs = MutableLiveData<List<WaterIntake>>()
    val waterLogs: LiveData<List<WaterIntake>> = _waterLogs

    private val _dailyGoal = MutableLiveData<Int>()
    val dailyGoal: LiveData<Int> = _dailyGoal

    init {
        _dailyGoal.value = 2500 // Default 2.5L goal
    }

    fun loadTodayWaterIntake(userId: String) {
        viewModelScope.launch {
            val today = Calendar.getInstance()
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)
            val startOfDay = today.time

            today.add(Calendar.DAY_OF_MONTH, 1)
            val endOfDay = today.time

            val totalIntake = waterIntakeDao.getTotalWaterIntakeByDateRange(userId, startOfDay, endOfDay) ?: 0
            _dailyWaterIntake.postValue(totalIntake)
        }
    }

    fun addWaterIntake(userId: String, amount: Int) {
        viewModelScope.launch {
            val waterIntake = WaterIntake(
                userId = userId,
                amount = amount,
                date = Date()
            )
            waterIntakeDao.insertWaterIntake(waterIntake)
            loadTodayWaterIntake(userId) // Refresh data
        }
    }

    fun getProgressPercentage(): Int {
        val intake = _dailyWaterIntake.value ?: 0
        val goal = _dailyGoal.value ?: 2500
        return ((intake.toFloat() / goal.toFloat()) * 100).toInt().coerceAtMost(100)
    }

    fun updateDailyGoal(newGoal: Int) {
        _dailyGoal.value = newGoal
    }
}
