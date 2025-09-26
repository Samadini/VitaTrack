package com.vitatrack.app.data.database

import androidx.room.TypeConverter
import com.vitatrack.app.data.model.MealType
import java.util.Date

class Converters {
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromMealType(mealType: MealType): String {
        return mealType.name
    }
    
    @TypeConverter
    fun toMealType(mealType: String): MealType {
        return MealType.valueOf(mealType)
    }
}
