package com.vitatrack.app.receivers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/vitatrack/app/receivers/NotificationReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_debug"})
public final class NotificationReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_WATER_REMINDER = "com.vitatrack.app.WATER_REMINDER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_EXERCISE_REMINDER = "com.vitatrack.app.EXERCISE_REMINDER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_MEAL_REMINDER = "com.vitatrack.app.MEAL_REMINDER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MEAL_TYPE = "meal_type";
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.receivers.NotificationReceiver.Companion Companion = null;
    
    public NotificationReceiver() {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/vitatrack/app/receivers/NotificationReceiver$Companion;", "", "()V", "ACTION_EXERCISE_REMINDER", "", "ACTION_MEAL_REMINDER", "ACTION_WATER_REMINDER", "EXTRA_MEAL_TYPE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}