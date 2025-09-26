package com.vitatrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String, // Firebase UID
    val name: String,
    val email: String,
    val age: Int? = null,
    val height: Float? = null, // in cm
    val weight: Float? = null, // in kg
    val dailyWaterGoal: Int = 2500, // in ml
    val dailyCalorieGoal: Int = 2000,
    val dailyStepsGoal: Int = 10000,
    val profileImageUrl: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
