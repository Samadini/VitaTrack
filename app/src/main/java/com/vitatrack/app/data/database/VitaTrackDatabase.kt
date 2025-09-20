package com.vitatrack.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vitatrack.app.data.dao.ExerciseDao
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.dao.UserDao
import com.vitatrack.app.data.dao.WaterIntakeDao
import com.vitatrack.app.data.model.Exercise
import com.vitatrack.app.data.model.Meal
import com.vitatrack.app.data.model.User
import com.vitatrack.app.data.model.WaterIntake

@Database(
    entities = [User::class, Exercise::class, Meal::class, WaterIntake::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VitaTrackDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun mealDao(): MealDao
    abstract fun waterIntakeDao(): WaterIntakeDao
    
    companion object {
        @Volatile
        private var INSTANCE: VitaTrackDatabase? = null
        
        fun getDatabase(context: Context): VitaTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VitaTrackDatabase::class.java,
                    "vita_track_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
