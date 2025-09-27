package com.vitatrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import java.util.UUID

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey
    @DocumentId
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val name: String = "",
    val type: MealType = MealType.OTHER,
    val calories: Int = 0,
    val protein: Float? = null, // in grams
    val carbs: Float? = null, // in grams
    val fat: Float? = null, // in grams
    val fiber: Float? = null, // in grams
    val sugar: Float? = null, // in grams
    val notes: String? = null,
    val date: Date = Date(),
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    OTHER
}
