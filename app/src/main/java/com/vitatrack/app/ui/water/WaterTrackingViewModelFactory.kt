package com.vitatrack.app.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitatrack.app.data.dao.WaterIntakeDao

class WaterTrackingViewModelFactory(
    private val waterIntakeDao: WaterIntakeDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterTrackingViewModel::class.java)) {
            return WaterTrackingViewModel(waterIntakeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
