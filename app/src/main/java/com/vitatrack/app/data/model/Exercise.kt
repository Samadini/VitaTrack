package com.vitatrack.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "exercises")
@Parcelize
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
) : Parcelable

@Parcelize
enum class ExerciseType : Parcelable {
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
