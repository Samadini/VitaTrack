package com.vitatrack.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.ui.auth.LoginActivity
import com.vitatrack.app.ui.main.MainActivityNew

class SplashActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "SplashActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate started")
        try {
            super.onCreate(savedInstanceState)
            Log.d(TAG, "super.onCreate completed")
            
            // Use simple layout instead of binding for now
            setContentView(R.layout.activity_splash)
            Log.d(TAG, "Layout set")
            
            // Check authentication status after delay
            Handler(Looper.getMainLooper()).postDelayed({
                checkAuthenticationStatus()
            }, 2000) // 2 seconds delay
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            // If there's an error, go directly to auth activity
            navigateToAuth()
        }
    }
    
    private fun checkAuthenticationStatus() {
        Log.d(TAG, "Checking authentication status")
        try {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            
            if (currentUser != null) {
                Log.d(TAG, "User is signed in, navigating to MainActivityNew")
                startActivity(Intent(this, MainActivityNew::class.java))
            } else {
                Log.d(TAG, "No user signed in, navigating to LoginActivity")
                navigateToAuth()
            }
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking authentication", e)
            navigateToAuth()
        }
    }
    
    private fun navigateToAuth() {
        Log.d(TAG, "Navigating to LoginActivity")
        try {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start LoginActivity", e)
        }
    }
}
