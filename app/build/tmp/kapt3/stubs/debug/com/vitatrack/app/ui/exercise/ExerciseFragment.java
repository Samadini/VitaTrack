package com.vitatrack.app.ui.exercise;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\u001a\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\u0013H\u0002J\u0014\u0010\"\u001a\u00020\u00132\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u0002J\u0010\u0010%\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$H\u0002J\u0016\u0010&\u001a\u00020\u00132\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020$0(H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006)"}, d2 = {"Lcom/vitatrack/app/ui/exercise/ExerciseFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/vitatrack/app/databinding/FragmentExerciseBinding;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "getBinding", "()Lcom/vitatrack/app/databinding/FragmentExerciseBinding;", "exerciseAdapter", "Lcom/vitatrack/app/ui/exercise/adapter/ExerciseAdapter;", "viewModel", "Lcom/vitatrack/app/ui/exercise/ExerciseViewModel;", "getViewModel", "()Lcom/vitatrack/app/ui/exercise/ExerciseViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "loadExercises", "", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupClickListeners", "setupRecyclerView", "showAddEditExerciseDialog", "exercise", "Lcom/vitatrack/app/data/model/Exercise;", "showDeleteConfirmationDialog", "updateUI", "exercises", "", "app_debug"})
public final class ExerciseFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.vitatrack.app.databinding.FragmentExerciseBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.vitatrack.app.ui.exercise.adapter.ExerciseAdapter exerciseAdapter;
    private com.google.firebase.auth.FirebaseAuth auth;
    
    public ExerciseFragment() {
        super();
    }
    
    private final com.vitatrack.app.databinding.FragmentExerciseBinding getBinding() {
        return null;
    }
    
    private final com.vitatrack.app.ui.exercise.ExerciseViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void loadExercises() {
    }
    
    private final void updateUI(java.util.List<com.vitatrack.app.data.model.Exercise> exercises) {
    }
    
    private final void showAddEditExerciseDialog(com.vitatrack.app.data.model.Exercise exercise) {
    }
    
    private final void showDeleteConfirmationDialog(com.vitatrack.app.data.model.Exercise exercise) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}