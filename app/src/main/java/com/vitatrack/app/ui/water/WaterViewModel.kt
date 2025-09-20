package com.vitatrack.app.ui.water

import androidx.lifecycle.*
import com.vitatrack.app.data.dao.WaterIntakeDao
import com.vitatrack.app.data.model.WaterIntake
import kotlinx.coroutines.launch
import java.util.*

class WaterViewModel(
    private val waterIntakeDao: WaterIntakeDao
) : ViewModel() {

    private val _waterIntakes = MutableLiveData<List<WaterIntake>>()
    val waterIntakes: LiveData<List<WaterIntake>> = _waterIntakes

    private val _dailyTotal = MutableLiveData<Int>()
    val dailyTotal: LiveData<Int> = _dailyTotal

    fun loadWaterData(userId: String) {
        viewModelScope.launch {
            try {
                // Load today's water intakes
                val today = Calendar.getInstance()
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

                // Observe water intakes for today
                waterIntakeDao.getWaterIntakeByDateRange(userId, startOfDay.time, endOfDay.time)
                    .observeForever { intakes ->
                        _waterIntakes.value = intakes
                        
                        // Calculate daily total
                        val total = intakes.sumOf { it.amount }
                        _dailyTotal.value = total
                    }

            } catch (e: Exception) {
                _waterIntakes.postValue(emptyList())
                _dailyTotal.postValue(0)
            }
        }
    }

    fun addWaterIntake(waterIntake: WaterIntake) {
        viewModelScope.launch {
            try {
                waterIntakeDao.insertWaterIntake(waterIntake)
                // Refresh data
                loadWaterData(waterIntake.userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteWaterIntake(waterIntake: WaterIntake) {
        viewModelScope.launch {
            try {
                waterIntakeDao.deleteWaterIntake(waterIntake)
                // Refresh data
                loadWaterData(waterIntake.userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

class WaterViewModelFactory(
    private val waterIntakeDao: WaterIntakeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterViewModel(waterIntakeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
