package com.vitatrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String? = null,
    val age: Int? = null,
    val height: Float? = null, // in cm
    val weight: Float? = null, // in kg
    val dailyStepsGoal: Int = 10000,
    val dailyCaloriesGoal: Int = 2000,
    val dailyWaterGoal: Int = 2000, // in ml
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
