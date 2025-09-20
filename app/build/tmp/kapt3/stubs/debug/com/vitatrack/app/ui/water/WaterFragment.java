package com.vitatrack.app.ui.water;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0013H\u0016J\u001a\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010#\u001a\u00020\u0013H\u0002J\b\u0010$\u001a\u00020\u0013H\u0002J\b\u0010%\u001a\u00020\u0013H\u0002J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010\'\u001a\u00020(H\u0002J\u0016\u0010)\u001a\u00020\u00132\f\u0010*\u001a\b\u0012\u0004\u0012\u00020(0+H\u0002J\u0010\u0010,\u001a\u00020\u00132\u0006\u0010-\u001a\u00020\u0015H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/vitatrack/app/ui/water/WaterFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/vitatrack/app/databinding/FragmentWaterBinding;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "getBinding", "()Lcom/vitatrack/app/databinding/FragmentWaterBinding;", "viewModel", "Lcom/vitatrack/app/ui/water/WaterViewModel;", "getViewModel", "()Lcom/vitatrack/app/ui/water/WaterViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "waterLogAdapter", "Lcom/vitatrack/app/ui/water/adapter/WaterLogAdapter;", "addWaterIntake", "", "amount", "", "loadWaterData", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupClickListeners", "setupRecyclerView", "showCustomAmountDialog", "showDeleteConfirmationDialog", "waterIntake", "Lcom/vitatrack/app/data/model/WaterIntake;", "updateEmptyState", "waterIntakes", "", "updateProgressUI", "totalMl", "app_debug"})
public final class WaterFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.vitatrack.app.databinding.FragmentWaterBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.vitatrack.app.ui.water.adapter.WaterLogAdapter waterLogAdapter;
    private com.google.firebase.auth.FirebaseAuth auth;
    
    public WaterFragment() {
        super();
    }
    
    private final com.vitatrack.app.databinding.FragmentWaterBinding getBinding() {
        return null;
    }
    
    private final com.vitatrack.app.ui.water.WaterViewModel getViewModel() {
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
    
    private final void loadWaterData() {
    }
    
    private final void addWaterIntake(int amount) {
    }
    
    private final void showCustomAmountDialog() {
    }
    
    private final void showDeleteConfirmationDialog(com.vitatrack.app.data.model.WaterIntake waterIntake) {
    }
    
    private final void updateProgressUI(int totalMl) {
    }
    
    private final void updateEmptyState(java.util.List<com.vitatrack.app.data.model.WaterIntake> waterIntakes) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}