package com.vitatrack.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/vitatrack/app/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "userRepository", "Lcom/vitatrack/app/data/repository/UserRepository;", "exerciseDao", "Lcom/vitatrack/app/data/dao/ExerciseDao;", "mealDao", "Lcom/vitatrack/app/data/dao/MealDao;", "waterIntakeDao", "Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "(Lcom/vitatrack/app/data/repository/UserRepository;Lcom/vitatrack/app/data/dao/ExerciseDao;Lcom/vitatrack/app/data/dao/MealDao;Lcom/vitatrack/app/data/dao/WaterIntakeDao;)V", "_dailyStats", "Landroidx/lifecycle/MutableLiveData;", "Lcom/vitatrack/app/ui/dashboard/DailyStats;", "_recentActivities", "", "Lcom/vitatrack/app/ui/dashboard/RecentActivity;", "dailyStats", "Landroidx/lifecycle/LiveData;", "getDailyStats", "()Landroidx/lifecycle/LiveData;", "recentActivities", "getRecentActivities", "loadDashboardData", "", "userId", "", "loadRecentActivities", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.ExerciseDao exerciseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.MealDao mealDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.vitatrack.app.ui.dashboard.DailyStats> _dailyStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.vitatrack.app.ui.dashboard.DailyStats> dailyStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.vitatrack.app.ui.dashboard.RecentActivity>> _recentActivities = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.ui.dashboard.RecentActivity>> recentActivities = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.ExerciseDao exerciseDao, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.MealDao mealDao, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.vitatrack.app.ui.dashboard.DailyStats> getDailyStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.ui.dashboard.RecentActivity>> getRecentActivities() {
        return null;
    }
    
    public final void loadDashboardData(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    private final void loadRecentActivities(java.lang.String userId) {
    }
}