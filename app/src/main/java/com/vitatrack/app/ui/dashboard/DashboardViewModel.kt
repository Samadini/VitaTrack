package com.vitatrack.app.ui.dashboard

import androidx.lifecycle.*
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.dao.WaterIntakeDao
import com.vitatrack.app.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(
    private val userRepository: UserRepository,
    private val mealDao: MealDao,
    private val waterIntakeDao: WaterIntakeDao
) : ViewModel() {

    private val _dailyStats = MutableLiveData<DailyStats>()
    val dailyStats: LiveData<DailyStats> = _dailyStats

    private val _recentActivities = MutableLiveData<List<RecentActivity>>()
    val recentActivities: LiveData<List<RecentActivity>> = _recentActivities

    fun loadDashboardData(userId: String) {
        viewModelScope.launch {
            try {
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

                // Load daily statistics
                val totalCaloriesFromMeals = mealDao.getTotalCaloriesByDateRange(
                    userId, startOfDay.time, endOfDay.time
                ) ?: 0

                // Placeholder values for exercise data
                val totalCaloriesBurned = 0
                val totalExerciseDuration = 0

                val totalWaterIntake = waterIntakeDao.getTotalWaterIntakeByDateRange(
                    userId, startOfDay.time, endOfDay.time
                ) ?: 0

                // For now, we'll use a placeholder for steps
                // In a real app, you'd integrate with step counter APIs
                val steps = (5000..12000).random()

                val stats = DailyStats(
                    steps = steps,
                    calories = totalCaloriesFromMeals,
                    waterIntake = totalWaterIntake,
                    exerciseDuration = totalExerciseDuration,
                    caloriesBurned = totalCaloriesBurned
                )

                _dailyStats.postValue(stats)

                // Load recent activities (placeholder data for now)
                loadRecentActivities(userId)

            } catch (e: Exception) {
                // Handle error
                _dailyStats.postValue(
                    DailyStats(
                        steps = 0,
                        calories = 0,
                        waterIntake = 0,
                        exerciseDuration = 0,
                        caloriesBurned = 0
                    )
                )
            }
        }
    }

    private fun loadRecentActivities(userId: String) {
        // For now, we'll show placeholder recent activities
        // In a real implementation, you'd query recent exercises, meals, etc.
        val activities = listOf(
            RecentActivity(
                title = "Morning Run",
                details = "30 minutes • 250 calories",
                time = "2h ago",
                type = ActivityType.EXERCISE
            ),
            RecentActivity(
                title = "Breakfast",
                details = "Oatmeal with fruits • 320 calories",
                time = "3h ago",
                type = ActivityType.MEAL
            ),
            RecentActivity(
                title = "Water Intake",
                details = "500ml added",
                time = "1h ago",
                type = ActivityType.WATER
            )
        )
        
        _recentActivities.postValue(activities)
    }
}

data class DailyStats(
    val steps: Int,
    val calories: Int,
    val waterIntake: Int, // in ml
    val exerciseDuration: Int, // in minutes
    val caloriesBurned: Int
)

data class RecentActivity(
    val title: String,
    val details: String,
    val time: String,
    val type: ActivityType
)

enum class ActivityType {
    EXERCISE, MEAL, WATER
}

class DashboardViewModelFactory(
    private val userRepository: UserRepository,
    private val mealDao: MealDao,
    private val waterIntakeDao: WaterIntakeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(userRepository, mealDao, waterIntakeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
