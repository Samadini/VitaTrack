package com.vitatrack.app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0012"}, d2 = {"Lcom/vitatrack/app/VitaTrackApplication;", "Landroid/app/Application;", "()V", "database", "Lcom/vitatrack/app/data/database/VitaTrackDatabase;", "getDatabase", "()Lcom/vitatrack/app/data/database/VitaTrackDatabase;", "database$delegate", "Lkotlin/Lazy;", "userRepository", "Lcom/vitatrack/app/data/repository/UserRepository;", "getUserRepository", "()Lcom/vitatrack/app/data/repository/UserRepository;", "userRepository$delegate", "createNotificationChannels", "", "onCreate", "Companion", "app_debug"})
public final class VitaTrackApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "VitaTrackApplication";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy userRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.VitaTrackApplication.Companion Companion = null;
    
    public VitaTrackApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vitatrack.app.data.database.VitaTrackDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vitatrack.app.data.repository.UserRepository getUserRepository() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void createNotificationChannels() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/vitatrack/app/VitaTrackApplication$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}