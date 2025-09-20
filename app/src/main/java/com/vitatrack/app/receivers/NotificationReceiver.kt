package com.vitatrack.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vitatrack.app.utils.NotificationHelper

class NotificationReceiver : BroadcastReceiver() {
    
    companion object {
        const val ACTION_WATER_REMINDER = "com.vitatrack.app.WATER_REMINDER"
        const val ACTION_EXERCISE_REMINDER = "com.vitatrack.app.EXERCISE_REMINDER"
        const val ACTION_MEAL_REMINDER = "com.vitatrack.app.MEAL_REMINDER"
        const val EXTRA_MEAL_TYPE = "meal_type"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_WATER_REMINDER -> {
                NotificationHelper.showWaterReminder(context)
            }
            ACTION_EXERCISE_REMINDER -> {
                NotificationHelper.showExerciseReminder(context)
            }
            ACTION_MEAL_REMINDER -> {
                val mealType = intent.getStringExtra(EXTRA_MEAL_TYPE) ?: "meal"
                NotificationHelper.showMealReminder(context, mealType)
            }
        }
    }
}
