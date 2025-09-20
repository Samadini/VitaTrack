package com.vitatrack.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/vitatrack/app/utils/NotificationHelper;", "", "()V", "EXERCISE_REMINDER_CHANNEL_ID", "", "EXERCISE_REMINDER_ID", "", "MEAL_REMINDER_CHANNEL_ID", "MEAL_REMINDER_ID", "WATER_REMINDER_CHANNEL_ID", "WATER_REMINDER_ID", "showExerciseReminder", "", "context", "Landroid/content/Context;", "showMealReminder", "mealType", "showWaterReminder", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WATER_REMINDER_CHANNEL_ID = "water_reminder_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXERCISE_REMINDER_CHANNEL_ID = "exercise_reminder_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MEAL_REMINDER_CHANNEL_ID = "meal_reminder_channel";
    public static final int WATER_REMINDER_ID = 1001;
    public static final int EXERCISE_REMINDER_ID = 1002;
    public static final int MEAL_REMINDER_ID = 1003;
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.utils.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    public final void showWaterReminder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void showExerciseReminder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void showMealReminder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String mealType) {
    }
}