package com.vitatrack.app.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vitatrack.app.utils.NotificationHelper

class VitaTrackMessagingService : FirebaseMessagingService() {
    
    companion object {
        private const val TAG = "VitaTrackMessaging"
    }
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        Log.d(TAG, "From: ${remoteMessage.from}")
        
        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataMessage(remoteMessage.data)
        }
        
        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Handle notification message
        }
    }
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        
        // Send token to your server
        sendRegistrationToServer(token)
    }
    
    private fun handleDataMessage(data: Map<String, String>) {
        val type = data["type"]
        when (type) {
            "water_reminder" -> {
                NotificationHelper.showWaterReminder(this)
            }
            "exercise_reminder" -> {
                NotificationHelper.showExerciseReminder(this)
            }
            "meal_reminder" -> {
                val mealType = data["meal_type"] ?: "meal"
                NotificationHelper.showMealReminder(this, mealType)
            }
        }
    }
    
    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement sending token to your server
        Log.d(TAG, "Token sent to server: $token")
    }
}
