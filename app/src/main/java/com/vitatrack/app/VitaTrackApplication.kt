package com.vitatrack.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.vitatrack.app.data.database.VitaTrackDatabase
import com.vitatrack.app.data.repository.UserRepository
import com.vitatrack.app.data.repository.MealRepository
import com.vitatrack.app.data.repository.ExerciseRepository
import com.vitatrack.app.data.repository.LocalExerciseRepository
import com.vitatrack.app.utils.NotificationHelper

class VitaTrackApplication : Application() {

    companion object {
        private const val TAG = "VitaTrackApplication"
    }

    // Database instance
    val database by lazy { VitaTrackDatabase.getDatabase(this) }
    
    // Repository instances
    val userRepository by lazy { UserRepository(database.userDao()) }
    val mealRepository by lazy { MealRepository(database.mealDao(), database.foodItemDao()) }
    val exerciseRepository by lazy { LocalExerciseRepository(ExerciseRepository()) }

    override fun onCreate() {
        super.onCreate()
        
        try {
            // Initialize Firebase
            FirebaseApp.initializeApp(this)
            Log.d(TAG, "Firebase initialized successfully")
            
            // Configure Firestore settings for better performance
            configureFirestore()
            
            // Configure Firebase Auth settings
            configureFirebaseAuth()
            
            // Create notification channels
            createNotificationChannels()
            Log.d(TAG, "Notification channels created")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error during application initialization", e)
            // Don't let the app crash on startup
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    NotificationHelper.WATER_REMINDER_CHANNEL_ID,
                    "Water Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders to drink water"
                },
                NotificationChannel(
                    NotificationHelper.EXERCISE_REMINDER_CHANNEL_ID,
                    "Exercise Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders to exercise"
                },
                NotificationChannel(
                    NotificationHelper.MEAL_REMINDER_CHANNEL_ID,
                    "Meal Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders for meals"
                }
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            channels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
    
    private fun configureFirestore() {
        try {
            val firestore = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
            firestore.firestoreSettings = settings
            Log.d(TAG, "Firestore configured successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error configuring Firestore", e)
        }
    }
    
    private fun configureFirebaseAuth() {
        try {
            val auth = FirebaseAuth.getInstance()
            // Enable auth state persistence
            auth.useAppLanguage()
            Log.d(TAG, "Firebase Auth configured successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error configuring Firebase Auth", e)
        }
    }

}
