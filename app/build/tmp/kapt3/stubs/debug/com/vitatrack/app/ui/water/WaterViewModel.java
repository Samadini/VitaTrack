package com.vitatrack.app.ui.water;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\nJ\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\nJ\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0018"}, d2 = {"Lcom/vitatrack/app/ui/water/WaterViewModel;", "Landroidx/lifecycle/ViewModel;", "waterIntakeDao", "Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "(Lcom/vitatrack/app/data/dao/WaterIntakeDao;)V", "_dailyTotal", "Landroidx/lifecycle/MutableLiveData;", "", "_waterIntakes", "", "Lcom/vitatrack/app/data/model/WaterIntake;", "dailyTotal", "Landroidx/lifecycle/LiveData;", "getDailyTotal", "()Landroidx/lifecycle/LiveData;", "waterIntakes", "getWaterIntakes", "addWaterIntake", "", "waterIntake", "deleteWaterIntake", "loadWaterData", "userId", "", "app_debug"})
public final class WaterViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> _waterIntakes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> waterIntakes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _dailyTotal = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> dailyTotal = null;
    
    public WaterViewModel(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> getWaterIntakes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getDailyTotal() {
        return null;
    }
    
    public final void loadWaterData(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void addWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake) {
    }
    
    public final void deleteWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake) {
    }
}