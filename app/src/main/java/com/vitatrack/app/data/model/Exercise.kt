package com.vitatrack.app.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Exercise(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val duration: Int = 0, // in minutes
    val caloriesBurned: Int = 0,
    val type: ExerciseType = ExerciseType.OTHER,
    val intensity: ExerciseIntensity = ExerciseIntensity.MODERATE,
    val notes: String? = null,
    val date: Date = Date(),
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

enum class ExerciseType {
    CARDIO,
    STRENGTH,
    FLEXIBILITY,
    SPORTS,
    WALKING,
    RUNNING,
    CYCLING,
    SWIMMING,
    YOGA,
    OTHER
}

enum class ExerciseIntensity {
    LOW,
    MODERATE,
    HIGH,
    VERY_HIGH
}
