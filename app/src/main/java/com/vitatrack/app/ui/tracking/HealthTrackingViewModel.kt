package com.vitatrack.app.ui.tracking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.data.model.Exercise
import com.vitatrack.app.data.model.ExerciseIntensity
import com.vitatrack.app.data.model.ExerciseType
import com.vitatrack.app.data.model.Meal
import com.vitatrack.app.data.model.MealType
import com.vitatrack.app.data.model.WaterIntake
import com.vitatrack.app.data.repository.ExerciseRepository
import com.vitatrack.app.data.repository.FirebaseMealRepository
import com.vitatrack.app.data.repository.FirebaseWaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HealthTrackingViewModel : ViewModel() {
    
    private val exerciseRepository = ExerciseRepository()
    private val mealRepository = FirebaseMealRepository()
    private val waterRepository = FirebaseWaterRepository()
    private val auth = FirebaseAuth.getInstance()
    
    // UI State
    private val _uiState = MutableStateFlow(HealthTrackingUiState())
    val uiState: StateFlow<HealthTrackingUiState> = _uiState.asStateFlow()
    
    companion object {
        private const val TAG = "HealthTrackingViewModel"
    }
    
    /**
     * Example: Add a new exercise
     */
    fun addExercise(
        name: String,
        duration: Int,
        caloriesBurned: Int,
        type: ExerciseType = ExerciseType.OTHER,
        intensity: ExerciseIntensity = ExerciseIntensity.MODERATE,
        notes: String? = null
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e(TAG, "User not authenticated")
            updateUiState { it.copy(error = "User not authenticated") }
            return
        }
        
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true, error = null) }
            
            val exercise = Exercise(
                userId = currentUser.uid,
                name = name,
                duration = duration,
                caloriesBurned = caloriesBurned,
                type = type,
                intensity = intensity,
                notes = notes,
                date = Date()
            )
            
            val result = exerciseRepository.addExercise(exercise)
            
            if (result.isSuccess) {
                Log.d(TAG, "Exercise added successfully with ID: ${result.getOrNull()}")
                updateUiState { 
                    it.copy(
                        isLoading = false, 
                        successMessage = "Exercise '$name' added successfully!"
                    ) 
                }
                // Refresh today's data
                loadTodayData()
            } else {
                val error = result.exceptionOrNull()?.message ?: "Failed to add exercise"
                Log.e(TAG, "Failed to add exercise: $error")
                updateUiState { it.copy(isLoading = false, error = error) }
            }
        }
    }
    
    /**
     * Example: Add a new meal
     */
    fun addMeal(
        name: String,
        calories: Int,
        type: MealType = MealType.OTHER,
        protein: Float? = null,
        carbs: Float? = null,
        fat: Float? = null,
        notes: String? = null
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e(TAG, "User not authenticated")
            updateUiState { it.copy(error = "User not authenticated") }
            return
        }
        
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true, error = null) }
            
            val meal = Meal(
                userId = currentUser.uid,
                name = name,
                type = type,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat,
                notes = notes,
                date = Date()
            )
            
            val result = mealRepository.addMeal(meal)
            
            if (result.isSuccess) {
                Log.d(TAG, "Meal added successfully with ID: ${result.getOrNull()}")
                updateUiState { 
                    it.copy(
                        isLoading = false, 
                        successMessage = "Meal '$name' added successfully!"
                    ) 
                }
                // Refresh today's data
                loadTodayData()
            } else {
                val error = result.exceptionOrNull()?.message ?: "Failed to add meal"
                Log.e(TAG, "Failed to add meal: $error")
                updateUiState { it.copy(isLoading = false, error = error) }
            }
        }
    }
    
    /**
     * Example: Add water intake
     */
    fun addWater(amountMl: Int, notes: String? = null) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e(TAG, "User not authenticated")
            updateUiState { it.copy(error = "User not authenticated") }
            return
        }
        
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true, error = null) }
            
            val result = waterRepository.addQuickWater(currentUser.uid, amountMl, notes)
            
            if (result.isSuccess) {
                Log.d(TAG, "Water intake added successfully with ID: ${result.getOrNull()}")
                updateUiState { 
                    it.copy(
                        isLoading = false, 
                        successMessage = "Added ${amountMl}ml water!"
                    ) 
                }
                // Refresh today's data
                loadTodayData()
            } else {
                val error = result.exceptionOrNull()?.message ?: "Failed to add water intake"
                Log.e(TAG, "Failed to add water intake: $error")
                updateUiState { it.copy(isLoading = false, error = error) }
            }
        }
    }
    
    /**
     * Load today's summary data
     */
    fun loadTodayData() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e(TAG, "User not authenticated")
            return
        }
        
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true, error = null) }
            
            try {
                // Load today's exercises
                val exercisesResult = exerciseRepository.getTodayExercises(currentUser.uid)
                val todayExercises = if (exercisesResult.isSuccess) {
                    exercisesResult.getOrNull() ?: emptyList()
                } else {
                    Log.e(TAG, "Failed to load exercises: ${exercisesResult.exceptionOrNull()?.message}")
                    emptyList()
                }
                
                // Load today's meals
                val mealsResult = mealRepository.getTodayMeals(currentUser.uid)
                val todayMeals = if (mealsResult.isSuccess) {
                    mealsResult.getOrNull() ?: emptyList()
                } else {
                    Log.e(TAG, "Failed to load meals: ${mealsResult.exceptionOrNull()?.message}")
                    emptyList()
                }
                
                // Load today's water intake
                val waterResult = waterRepository.getTodayWaterIntake(currentUser.uid)
                val todayWaterIntake = if (waterResult.isSuccess) {
                    waterResult.getOrNull() ?: 0
                } else {
                    Log.e(TAG, "Failed to load water intake: ${waterResult.exceptionOrNull()?.message}")
                    0
                }
                
                // Calculate totals
                val totalCaloriesBurned = todayExercises.sumOf { it.caloriesBurned }
                val totalExerciseDuration = todayExercises.sumOf { it.duration }
                val totalCaloriesConsumed = todayMeals.sumOf { it.calories }
                
                updateUiState { 
                    it.copy(
                        isLoading = false,
                        todayExercises = todayExercises,
                        todayMeals = todayMeals,
                        todayWaterIntake = todayWaterIntake,
                        totalCaloriesBurned = totalCaloriesBurned,
                        totalExerciseDuration = totalExerciseDuration,
                        totalCaloriesConsumed = totalCaloriesConsumed
                    ) 
                }
                
                Log.d(TAG, "Today's data loaded successfully")
                
            } catch (e: Exception) {
                Log.e(TAG, "Error loading today's data", e)
                updateUiState { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
    
    /**
     * Example: Quick add common exercises
     */
    fun addQuickExercise(exerciseType: QuickExerciseType) {
        when (exerciseType) {
            QuickExerciseType.WALK_30MIN -> {
                addExercise(
                    name = "Walking",
                    duration = 30,
                    caloriesBurned = 150,
                    type = ExerciseType.WALKING,
                    intensity = ExerciseIntensity.MODERATE
                )
            }
            QuickExerciseType.RUN_30MIN -> {
                addExercise(
                    name = "Running",
                    duration = 30,
                    caloriesBurned = 300,
                    type = ExerciseType.RUNNING,
                    intensity = ExerciseIntensity.HIGH
                )
            }
            QuickExerciseType.YOGA_45MIN -> {
                addExercise(
                    name = "Yoga Session",
                    duration = 45,
                    caloriesBurned = 180,
                    type = ExerciseType.YOGA,
                    intensity = ExerciseIntensity.LOW
                )
            }
        }
    }
    
    /**
     * Example: Quick add common meals
     */
    fun addQuickMeal(mealType: QuickMealType) {
        when (mealType) {
            QuickMealType.BREAKFAST_OATMEAL -> {
                addMeal(
                    name = "Oatmeal with Fruits",
                    calories = 320,
                    type = MealType.BREAKFAST,
                    protein = 12f,
                    carbs = 54f,
                    fat = 8f
                )
            }
            QuickMealType.LUNCH_SALAD -> {
                addMeal(
                    name = "Chicken Caesar Salad",
                    calories = 450,
                    type = MealType.LUNCH,
                    protein = 35f,
                    carbs = 15f,
                    fat = 28f
                )
            }
            QuickMealType.SNACK_APPLE -> {
                addMeal(
                    name = "Apple",
                    calories = 95,
                    type = MealType.SNACK,
                    protein = 0.5f,
                    carbs = 25f,
                    fat = 0.3f
                )
            }
        }
    }
    
    /**
     * Clear success message
     */
    fun clearSuccessMessage() {
        updateUiState { it.copy(successMessage = null) }
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        updateUiState { it.copy(error = null) }
    }
    
    private fun updateUiState(update: (HealthTrackingUiState) -> HealthTrackingUiState) {
        _uiState.value = update(_uiState.value)
    }
    
    init {
        // Load today's data when ViewModel is created
        loadTodayData()
    }
}

data class HealthTrackingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val todayExercises: List<Exercise> = emptyList(),
    val todayMeals: List<Meal> = emptyList(),
    val todayWaterIntake: Int = 0,
    val totalCaloriesBurned: Int = 0,
    val totalExerciseDuration: Int = 0,
    val totalCaloriesConsumed: Int = 0
)

enum class QuickExerciseType {
    WALK_30MIN,
    RUN_30MIN,
    YOGA_45MIN
}

enum class QuickMealType {
    BREAKFAST_OATMEAL,
    LUNCH_SALAD,
    SNACK_APPLE
}
