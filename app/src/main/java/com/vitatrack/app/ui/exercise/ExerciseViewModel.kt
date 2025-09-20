package com.vitatrack.app.ui.exercise

import androidx.lifecycle.*
import com.vitatrack.app.data.dao.ExerciseDao
import com.vitatrack.app.data.model.Exercise
import kotlinx.coroutines.launch
import java.util.*

class ExerciseViewModel(
    private val exerciseDao: ExerciseDao
) : ViewModel() {

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _todayExerciseDuration = MutableLiveData<Int>()
    val todayExerciseDuration: LiveData<Int> = _todayExerciseDuration

    fun loadExercises(userId: String) {
        viewModelScope.launch {
            try {
                // Load all exercises for the user
                exerciseDao.getExercisesByUserId(userId).observeForever { exerciseList ->
                    _exercises.value = exerciseList
                }

                // Calculate today's exercise duration
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

                val todayDuration = exerciseDao.getTotalDurationByDateRange(
                    userId, startOfDay.time, endOfDay.time
                ) ?: 0

                _todayExerciseDuration.postValue(todayDuration)

            } catch (e: Exception) {
                _exercises.postValue(emptyList())
                _todayExerciseDuration.postValue(0)
            }
        }
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                exerciseDao.insertExercise(exercise)
                // Refresh the list
                loadExercises(exercise.userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                exerciseDao.updateExercise(exercise)
                // Refresh the list
                loadExercises(exercise.userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                exerciseDao.deleteExercise(exercise)
                // Refresh the list
                loadExercises(exercise.userId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

class ExerciseViewModelFactory(
    private val exerciseDao: ExerciseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseViewModel(exerciseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
