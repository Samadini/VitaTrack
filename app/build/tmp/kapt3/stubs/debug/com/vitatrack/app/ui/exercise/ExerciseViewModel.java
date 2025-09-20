package com.vitatrack.app.ui.exercise;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bJ\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/vitatrack/app/ui/exercise/ExerciseViewModel;", "Landroidx/lifecycle/ViewModel;", "exerciseDao", "Lcom/vitatrack/app/data/dao/ExerciseDao;", "(Lcom/vitatrack/app/data/dao/ExerciseDao;)V", "_exercises", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/vitatrack/app/data/model/Exercise;", "_todayExerciseDuration", "", "exercises", "Landroidx/lifecycle/LiveData;", "getExercises", "()Landroidx/lifecycle/LiveData;", "todayExerciseDuration", "getTodayExerciseDuration", "addExercise", "", "exercise", "deleteExercise", "loadExercises", "userId", "", "updateExercise", "app_debug"})
public final class ExerciseViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.vitatrack.app.data.dao.ExerciseDao exerciseDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.vitatrack.app.data.model.Exercise>> _exercises = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.Exercise>> exercises = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _todayExerciseDuration = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> todayExerciseDuration = null;
    
    public ExerciseViewModel(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.dao.ExerciseDao exerciseDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.vitatrack.app.data.model.Exercise>> getExercises() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTodayExerciseDuration() {
        return null;
    }
    
    public final void loadExercises(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void addExercise(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.Exercise exercise) {
    }
    
    public final void updateExercise(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.Exercise exercise) {
    }
    
    public final void deleteExercise(@org.jetbrains.annotations.NotNull()
    com.vitatrack.app.data.model.Exercise exercise) {
    }
}