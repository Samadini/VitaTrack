package com.vitatrack.app.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.vitatrack.app.R
import com.vitatrack.app.ui.main.MainActivityNew

object NotificationHelper {
    
    const val WATER_REMINDER_CHANNEL_ID = "water_reminder_channel"
    const val EXERCISE_REMINDER_CHANNEL_ID = "exercise_reminder_channel"
    const val MEAL_REMINDER_CHANNEL_ID = "meal_reminder_channel"
    
    const val WATER_REMINDER_ID = 1001
    const val EXERCISE_REMINDER_ID = 1002
    const val MEAL_REMINDER_ID = 1003

    fun showWaterReminder(context: Context) {
        val intent = Intent(context, MainActivityNew::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, WATER_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_water)
            .setContentTitle("Time to drink water!")
            .setContentText("Stay hydrated! Don't forget to log your water intake.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(WATER_REMINDER_ID, notification)
    }

    fun showExerciseReminder(context: Context) {
        val intent = Intent(context, MainActivityNew::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, EXERCISE_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_exercise)
            .setContentTitle("Time to exercise!")
            .setContentText("Get moving! Your body will thank you.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(EXERCISE_REMINDER_ID, notification)
    }

    fun showMealReminder(context: Context, mealType: String) {
        val intent = Intent(context, MainActivityNew::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, MEAL_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_meal)
            .setContentTitle("Time for $mealType!")
            .setContentText("Don't forget to log your meal and track your nutrition.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(MEAL_REMINDER_ID, notification)
    }
}
