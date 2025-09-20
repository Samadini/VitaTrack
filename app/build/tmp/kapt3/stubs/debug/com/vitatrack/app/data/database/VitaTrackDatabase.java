package com.vitatrack.app.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\f"}, d2 = {"Lcom/vitatrack/app/data/database/VitaTrackDatabase;", "Landroidx/room/RoomDatabase;", "()V", "exerciseDao", "Lcom/vitatrack/app/data/dao/ExerciseDao;", "mealDao", "Lcom/vitatrack/app/data/dao/MealDao;", "userDao", "Lcom/vitatrack/app/data/dao/UserDao;", "waterIntakeDao", "Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.vitatrack.app.data.model.User.class, com.vitatrack.app.data.model.Exercise.class, com.vitatrack.app.data.model.Meal.class, com.vitatrack.app.data.model.WaterIntake.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.vitatrack.app.data.database.Converters.class})
public abstract class VitaTrackDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.vitatrack.app.data.database.VitaTrackDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.data.database.VitaTrackDatabase.Companion Companion = null;
    
    public VitaTrackDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vitatrack.app.data.dao.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vitatrack.app.data.dao.ExerciseDao exerciseDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vitatrack.app.data.dao.MealDao mealDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/vitatrack/app/data/database/VitaTrackDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/vitatrack/app/data/database/VitaTrackDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.vitatrack.app.data.database.VitaTrackDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}