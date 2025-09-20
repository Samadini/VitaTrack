package com.vitatrack.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ%\u0010\u000b\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\f0\u000fH\u0016\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/vitatrack/app/ui/dashboard/DashboardViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "userRepository", "Lcom/vitatrack/app/data/repository/UserRepository;", "exerciseDao", "Lcom/vitatrack/app/data/dao/ExerciseDao;", "mealDao", "Lcom/vitatrack/app/data/dao/MealDao;", "waterIntakeDao", "Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "(Lcom/vitatrack/app/data/repository/UserRepository;Lcom/vitatrack/app/data/dao/ExerciseDao;Lcom/vitatrack/app/data/dao/MealDao;Lcom/vitatrack/app/data/dao/WaterIntakeDao;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
public final class DashboardViewModelFactory implements androidx.lifecycle.ViewModelProvider.Factory {
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.ExerciseDao exerciseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.MealDao mealDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao = null;
    
    public DashboardViewModelFactory(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.ExerciseDao exerciseDao, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.MealDao mealDao, @org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
    java.lang.Class<T> modelClass) {
        return null;
    }
}