package com.vitatrack.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.vitatrack.app.data.database.VitaTrackDatabase
import com.vitatrack.app.data.repository.UserRepository
import com.vitatrack.app.utils.NotificationHelper
import com.vitatrack.app.utils.FirebaseConfigValidator

class VitaTrackApplication : Application() {

    companion object {
        private const val TAG = "VitaTrackApplication"
    }

    // Database instance
    val database by lazy { VitaTrackDatabase.getDatabase(this) }
    
    // Repository instance
    val userRepository by lazy { UserRepository(database.userDao()) }

    override fun onCreate() {
        super.onCreate()
        
        try {
            // Initialize Firebase
            FirebaseApp.initializeApp(this)
            Log.d(TAG, "Firebase initialized successfully")
            
            // Create notification channels
            createNotificationChannels()
            Log.d(TAG, "Notification channels created")
            
            // Firebase validation (commented out to avoid startup crashes)
            // FirebaseConfigValidator.validate(this)
            
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

}
