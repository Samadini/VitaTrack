package com.vitatrack.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ&\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0015J*\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00180\u00172\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00180\u00172\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/vitatrack/app/data/repository/WaterIntakeRepository;", "", "waterIntakeDao", "Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "(Lcom/vitatrack/app/data/dao/WaterIntakeDao;)V", "deleteWaterIntake", "", "waterIntake", "Lcom/vitatrack/app/data/model/WaterIntake;", "(Lcom/vitatrack/app/data/model/WaterIntake;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWaterIntakeById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalWaterIntakeByDateRange", "", "userId", "", "startDate", "Ljava/util/Date;", "endDate", "(Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWaterIntakeByDateRange", "Landroidx/lifecycle/LiveData;", "", "getWaterIntakeById", "getWaterIntakeByUserId", "insertWaterIntake", "updateWaterIntake", "app_debug"})
public final class WaterIntakeRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao = null;
    
    public WaterIntakeRepository(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.WaterIntakeDao waterIntakeDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> getWaterIntakeByUserId(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> getWaterIntakeByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWaterIntakeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vitatrack.app.data.model.WaterIntake> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTotalWaterIntakeByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteWaterIntakeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}