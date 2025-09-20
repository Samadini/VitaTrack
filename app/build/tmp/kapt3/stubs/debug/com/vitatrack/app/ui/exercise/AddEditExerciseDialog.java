package com.vitatrack.app.ui.exercise;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0012\u001a\u00020\fH\u0016J\b\u0010\u0013\u001a\u00020\fH\u0002J\u001a\u0010\u0014\u001a\u00020\f2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000bJ\b\u0010\u0016\u001a\u00020\fH\u0002J\b\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/vitatrack/app/ui/exercise/AddEditExerciseDialog;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lcom/vitatrack/app/databinding/DialogAddEditExerciseBinding;", "binding", "getBinding", "()Lcom/vitatrack/app/databinding/DialogAddEditExerciseBinding;", "exercise", "Lcom/vitatrack/app/data/model/Exercise;", "onExerciseSavedListener", "Lkotlin/Function1;", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onDestroyView", "saveExercise", "setOnExerciseSavedListener", "listener", "setupClickListeners", "setupUI", "validateInput", "", "Companion", "app_debug"})
public final class AddEditExerciseDialog extends androidx.fragment.app.DialogFragment {
    @org.jetbrains.annotations.Nullable()
    private com.vitatrack.app.databinding.DialogAddEditExerciseBinding _binding;
    @org.jetbrains.annotations.Nullable()
    private com.vitatrack.app.data.model.Exercise exercise;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.vitatrack.app.data.model.Exercise, kotlin.Unit> onExerciseSavedListener;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_EXERCISE = "exercise";
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.ui.exercise.AddEditExerciseDialog.Companion Companion = null;
    
    public AddEditExerciseDialog() {
        super();
    }
    
    private final com.vitatrack.app.databinding.DialogAddEditExerciseBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.app.Dialog onCreateDialog(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void setupUI() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final boolean validateInput() {
        return false;
    }
    
    private final void saveExercise() {
    }
    
    public final void setOnExerciseSavedListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.vitatrack.app.data.model.Exercise, kotlin.Unit> listener) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/vitatrack/app/ui/exercise/AddEditExerciseDialog$Companion;", "", "()V", "ARG_EXERCISE", "", "newInstance", "Lcom/vitatrack/app/ui/exercise/AddEditExerciseDialog;", "exercise", "Lcom/vitatrack/app/data/model/Exercise;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.vitatrack.app.ui.exercise.AddEditExerciseDialog newInstance(@org.jetbrains.annotations.Nullable()
        com.vitatrack.app.data.model.Exercise exercise) {
            return null;
        }
    }
}