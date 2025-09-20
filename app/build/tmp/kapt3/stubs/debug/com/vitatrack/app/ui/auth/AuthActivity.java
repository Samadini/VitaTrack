package com.vitatrack.app.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0002J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\"\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0012\u0010\u0019\u001a\u00020\u000e2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u000eH\u0002J\b\u0010\u001d\u001a\u00020\u000eH\u0002J\b\u0010\u001e\u001a\u00020\u000eH\u0002J\u001c\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u00102\n\u0010!\u001a\u00060\"j\u0002`#H\u0002J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\fH\u0002J\b\u0010&\u001a\u00020\u000eH\u0002J\b\u0010\'\u001a\u00020\u000eH\u0002J\b\u0010(\u001a\u00020\u000eH\u0002J\b\u0010)\u001a\u00020\u000eH\u0002J\b\u0010*\u001a\u00020\u000eH\u0002J$\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\u00102\u0006\u0010-\u001a\u00020\u00102\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/vitatrack/app/ui/auth/AuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/vitatrack/app/databinding/ActivityAuthBinding;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "isLoginMode", "", "firebaseAuthWithGoogle", "", "idToken", "", "initializeFirebase", "navigateToMain", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resetPassword", "setupClickListeners", "setupGoogleSignIn", "showAuthError", "prefix", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "showLoading", "show", "signInWithEmail", "signInWithGoogle", "signUpWithEmail", "toggleMode", "updateUIForMode", "validateInput", "email", "password", "username", "Companion", "app_debug"})
public final class AuthActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.vitatrack.app.databinding.ActivityAuthBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.firestore.FirebaseFirestore firestore;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    private boolean isLoginMode = true;
    private static final int RC_SIGN_IN = 9001;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthActivity";
    @org.jetbrains.annotations.NotNull()
    public static final com.vitatrack.app.ui.auth.AuthActivity.Companion Companion = null;
    
    public AuthActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeFirebase() {
    }
    
    private final void setupGoogleSignIn() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void toggleMode() {
    }
    
    private final void updateUIForMode() {
    }
    
    private final void signInWithEmail() {
    }
    
    private final void signUpWithEmail() {
    }
    
    private final void signInWithGoogle() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void firebaseAuthWithGoogle(java.lang.String idToken) {
    }
    
    private final void resetPassword() {
    }
    
    private final boolean validateInput(java.lang.String email, java.lang.String password, java.lang.String username) {
        return false;
    }
    
    private final void showLoading(boolean show) {
    }
    
    private final void navigateToMain() {
    }
    
    private final void showAuthError(java.lang.String prefix, java.lang.Exception e) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/vitatrack/app/ui/auth/AuthActivity$Companion;", "", "()V", "RC_SIGN_IN", "", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}