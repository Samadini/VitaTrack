package com.vitatrack.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.databinding.ActivityLoginBinding
import com.vitatrack.app.ui.main.MainActivityNew
import com.vitatrack.app.utils.FirebaseUtils
import com.vitatrack.app.utils.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    
    companion object {
        private const val TAG = "LoginActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase and check connectivity
        initializeFirebaseAndNetwork()
        
        setupClickListeners()
    }
    
    private fun initializeFirebaseAndNetwork() {
        // Initialize Firebase
        if (!FirebaseUtils.initializeFirebase(this)) {
            Log.e(TAG, "Failed to initialize Firebase")
            showError("Failed to initialize Firebase. Please restart the app.")
            return
        }
        
        // Initialize Firebase Auth
        try {
            auth = FirebaseAuth.getInstance()
            Log.d(TAG, "Firebase Auth initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Firebase Auth", e)
            showError("Authentication service unavailable. Please restart the app.")
            return
        }
        
        // Register network callback for real-time network monitoring
        NetworkUtils.registerNetworkCallback(this)
        
        // Observe network changes
        NetworkUtils.isNetworkAvailable.observe(this) { isAvailable ->
            Log.d(TAG, "Network availability changed: $isAvailable")
            updateUIForNetworkState(isAvailable)
        }
        
        // Check if user is already signed in
        checkCurrentUser()
    }
    
    private fun checkCurrentUser() {
        lifecycleScope.launch {
            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    Log.d(TAG, "User already signed in: ${currentUser.email}")
                    // Add a small delay to show the login screen briefly
                    delay(1000)
                    navigateToMain()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking current user", e)
            }
        }
    }
    
    private fun updateUIForNetworkState(isAvailable: Boolean) {
        if (!isAvailable) {
            binding.btnSignIn.isEnabled = false
            binding.btnSignIn.text = "No Internet Connection"
        } else {
            binding.btnSignIn.isEnabled = true
            binding.btnSignIn.text = "Sign In"
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                signInUser(email, password)
            }
        }
        
        binding.tvCreateAccount.setOnClickListener {
            // Navigate to registration page
            startActivity(Intent(this, SimpleAuthActivity::class.java))
            finish()
        }
        
        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google Sign-In coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnApple.setOnClickListener {
            Toast.makeText(this, "Apple Sign-In coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordReset(email)
            } else {
                Toast.makeText(this, "Please enter your email first", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Please enter a valid email"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        
        return true
    }
    
    private fun signInUser(email: String, password: String) {
        Log.d(TAG, "Attempting to sign in user: $email")
        
        // Check network connectivity first
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showError("No internet connection. Please check your network and try again.")
            return
        }
        
        // Check if Firebase is ready
        if (!FirebaseUtils.isAuthReady()) {
            showError("Authentication service is not ready. Please restart the app.")
            return
        }
        
        // Update UI to show loading state
        setLoadingState(true)
        
        lifecycleScope.launch {
            try {
                // Add a small delay to prevent rapid successive calls
                delay(500)
                
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        setLoadingState(false)
                        
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Log.d(TAG, "Sign in successful for user: ${user?.email}")
                            
                            // Verify user is properly authenticated
                            if (user != null && user.isEmailVerified) {
                                showSuccess("Welcome back!")
                                navigateToMain()
                            } else if (user != null && !user.isEmailVerified) {
                                showError("Please verify your email address before signing in.")
                                auth.signOut()
                            } else {
                                showError("Authentication failed. Please try again.")
                            }
                        } else {
                            Log.e(TAG, "Sign in failed", task.exception)
                            handleSignInError(task.exception)
                        }
                    }
                    .addOnFailureListener { exception ->
                        setLoadingState(false)
                        Log.e(TAG, "Sign in failure", exception)
                        handleSignInError(exception)
                    }
            } catch (e: Exception) {
                setLoadingState(false)
                Log.e(TAG, "Unexpected error during sign in", e)
                handleSignInError(e)
            }
        }
    }
    
    private fun setLoadingState(isLoading: Boolean) {
        binding.btnSignIn.isEnabled = !isLoading
        binding.btnSignIn.text = if (isLoading) "Signing in..." else "Sign In"
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }
    
    private fun handleSignInError(exception: Exception?) {
        val errorMessage = FirebaseUtils.getFirebaseErrorMessage(exception)
        
        Log.e(TAG, "Sign in error: $errorMessage", exception)
        showError(errorMessage)
        
        // Show retry option for network errors
        if (FirebaseUtils.isNetworkError(exception)) {
            showRetryDialog()
        }
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    
    private fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun showRetryDialog() {
        val networkType = NetworkUtils.getNetworkType(this)
        val message = "Unable to connect to Firebase.\n\nNetwork: $networkType\n\nWould you like to retry?"
        
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Connection Error")
            .setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                if (validateInput(email, password)) {
                    signInUser(email, password)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun sendPasswordReset(email: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showError("No internet connection. Please check your network and try again.")
            return
        }
        
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSuccess("Password reset email sent to $email")
                } else {
                    val errorMessage = FirebaseUtils.getFirebaseErrorMessage(task.exception)
                    showError("Failed to send reset email: $errorMessage")
                }
            }
    }
    
    private fun navigateToMain() {
        try {
            val intent = Intent(this, MainActivityNew::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error navigating to main activity", e)
            showError("Error opening main screen. Please restart the app.")
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Clean up any resources if needed
        try {
            // Remove observers to prevent memory leaks
            NetworkUtils.isNetworkAvailable.removeObservers(this)
        } catch (e: Exception) {
            Log.e(TAG, "Error during cleanup", e)
        }
    }
}
