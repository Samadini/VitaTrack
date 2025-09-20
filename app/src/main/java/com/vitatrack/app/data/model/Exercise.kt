package com.vitatrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val type: ExerciseType,
    val name: String,
    val duration: Int, // in minutes
    val caloriesBurned: Int,
    val notes: String? = null,
    val date: Date = Date(),
    val createdAt: Date = Date()
)

enum class ExerciseType {
    WALKING,
    RUNNING,
    CYCLING,
    SWIMMING,
    YOGA,
    GYM,
    CARDIO,
    STRENGTH,
    SPORTS,
    OTHER
}
