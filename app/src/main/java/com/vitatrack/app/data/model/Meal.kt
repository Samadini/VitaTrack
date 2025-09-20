package com.vitatrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val name: String,
    val type: MealType,
    val calories: Int,
    val protein: Float? = null, // in grams
    val carbs: Float? = null, // in grams
    val fat: Float? = null, // in grams
    val fiber: Float? = null, // in grams
    val sugar: Float? = null, // in grams
    val notes: String? = null,
    val date: Date = Date(),
    val createdAt: Date = Date()
)

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    OTHER
}
