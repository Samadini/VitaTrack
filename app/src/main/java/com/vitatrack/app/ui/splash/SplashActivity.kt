package com.vitatrack.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.databinding.ActivitySplashBinding
import com.vitatrack.app.ui.auth.AuthActivity
import com.vitatrack.app.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        
        // Add animation to logo
        animateLogo()
        
        // Check authentication status after delay
        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthenticationStatus()
        }, 2500) // 2.5 seconds delay
    }
    
    private fun animateLogo() {
        binding.logoContainer.alpha = 0f
        binding.logoContainer.scaleX = 0.5f
        binding.logoContainer.scaleY = 0.5f
        
        binding.logoContainer.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .start()
    }
    
    private fun checkAuthenticationStatus() {
        val currentUser = auth.currentUser
        
        if (currentUser != null) {
            // User is signed in, go to main activity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // No user is signed in, go to authentication
            startActivity(Intent(this, AuthActivity::class.java))
        }
        
        finish()
    }
}
