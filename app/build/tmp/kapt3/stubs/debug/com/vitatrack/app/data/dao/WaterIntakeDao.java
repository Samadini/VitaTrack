package com.vitatrack.app.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0012J,\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00150\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\'J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00150\u00142\u0006\u0010\r\u001a\u00020\u000eH\'J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u001a"}, d2 = {"Lcom/vitatrack/app/data/dao/WaterIntakeDao;", "", "deleteWaterIntake", "", "waterIntake", "Lcom/vitatrack/app/data/model/WaterIntake;", "(Lcom/vitatrack/app/data/model/WaterIntake;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWaterIntakeById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalWaterIntakeByDateRange", "", "userId", "", "startDate", "Ljava/util/Date;", "endDate", "(Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWaterIntakeByDateRange", "Landroidx/lifecycle/LiveData;", "", "getWaterIntakeById", "getWaterIntakeByUserId", "insertWaterIntake", "updateWaterIntake", "app_debug"})
@androidx.room.Dao()
public abstract interface WaterIntakeDao {
    
    @androidx.room.Query(value = "SELECT * FROM water_intake WHERE userId = :userId ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> getWaterIntakeByUserId(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    @androidx.room.Query(value = "SELECT * FROM water_intake WHERE userId = :userId AND date >= :startDate AND date <= :endDate ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.WaterIntake>> getWaterIntakeByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate);
    
    @androidx.room.Query(value = "SELECT * FROM water_intake WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWaterIntakeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vitatrack.app.data.model.WaterIntake> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM water_intake WHERE userId = :userId AND date >= :startDate AND date <= :endDate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalWaterIntakeByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteWaterIntake(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.WaterIntake waterIntake, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM water_intake WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteWaterIntakeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}