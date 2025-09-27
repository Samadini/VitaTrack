package com.vitatrack.app.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vitatrack.app.databinding.ActivitySplashBinding
import com.vitatrack.app.ui.auth.LoginActivity
import com.vitatrack.app.ui.main.MainActivityNew

class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    
    companion object {
        private const val TAG = "SplashActivity"
        private const val SPLASH_DELAY = 3000L // 3 seconds
        private const val PREFS_NAME = "VitaTrackPrefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate started")
        try {
            super.onCreate(savedInstanceState)
            Log.d(TAG, "super.onCreate completed")
            
            // Initialize ViewBinding and load activity_splash.xml
            binding = ActivitySplashBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d(TAG, "Layout set with ViewBinding")
            
            // Check authentication status after 3-second delay
            Handler(Looper.getMainLooper()).postDelayed({
                checkAuthenticationAndNavigate()
            }, SPLASH_DELAY)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            // If there's an error, go directly to login
            navigateToLogin()
        }
    }
    
    private fun checkAuthenticationAndNavigate() {
        Log.d(TAG, "Checking login status from SharedPreferences")
        try {
            val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val isLoggedIn = sharedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)
            val userEmail = sharedPrefs.getString(KEY_USER_EMAIL, "")
            
            if (isLoggedIn && !userEmail.isNullOrEmpty()) {
                Log.d(TAG, "User is logged in: $userEmail, navigating to MainActivityNew")
                navigateToMainActivity()
            } else {
                Log.d(TAG, "User not logged in, navigating to LoginActivity")
                navigateToLogin()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking login status", e)
            navigateToLogin()
        }
    }
    
    private fun navigateToMainActivity() {
        Log.d(TAG, "Navigating to MainActivityNew")
        try {
            val intent = Intent(this, MainActivityNew::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start MainActivityNew", e)
            // Fallback to login if main activity fails
            navigateToLogin()
        }
    }
    
    private fun navigateToLogin() {
        Log.d(TAG, "Navigating to LoginActivity")
        try {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start LoginActivity", e)
        }
    }
}
