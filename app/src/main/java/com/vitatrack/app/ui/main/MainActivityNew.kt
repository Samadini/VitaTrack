package com.vitatrack.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.databinding.ActivityMainNewBinding
import com.vitatrack.app.ui.auth.SimpleAuthActivity

class MainActivityNew : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainNewBinding
    private lateinit var auth: FirebaseAuth
    
    companion object {
        private const val TAG = "MainActivityNew"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivityNew onCreate started")
        
        // Initialize binding and auth first
        binding = ActivityMainNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        
        // Check if user is logged in
        if (auth.currentUser == null) {
            Log.d(TAG, "No user logged in, redirecting to auth")
            redirectToAuth()
            return
        }
        
        Log.d(TAG, "User is logged in: ${auth.currentUser?.email}")
        
        // Setup components with individual error handling
        setupNavigation()
        setupToolbar()
        
        Log.d(TAG, "MainActivityNew onCreate completed successfully")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivityNew onResume")
        
        // Double-check user is still logged in
        if (auth.currentUser == null) {
            Log.w(TAG, "User session lost, redirecting to auth")
            redirectToAuth()
        } else {
            Log.d(TAG, "User session valid: ${auth.currentUser?.email}")
        }
    }
    
    private fun setupNavigation() {
        try {
            // Wait a bit for the layout to be fully inflated
            binding.root.post {
                try {
                    val navHostFragment = supportFragmentManager
                        .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
                    
                    if (navHostFragment == null) {
                        Log.e(TAG, "NavHostFragment not found")
                        return@post
                    }
                    
                    val navController = navHostFragment.navController
                    val bottomNav = binding.bottomNavigation
                    
                    if (bottomNav == null) {
                        Log.e(TAG, "BottomNavigationView not found")
                        return@post
                    }
                    
                    bottomNav.setupWithNavController(navController)
                    Log.d(TAG, "Navigation setup completed successfully")
                    
                } catch (e: Exception) {
                    Log.e(TAG, "Error in delayed navigation setup", e)
                    // Don't redirect to auth here, just log the error
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up navigation", e)
            // Don't redirect to auth immediately, let the app try to continue
        }
    }
    
    private fun setupToolbar() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = "VitaTrack"
            Log.d(TAG, "Toolbar setup completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up toolbar", e)
            // Toolbar setup failure is not critical, continue without crashing
        }
    }
    
    private fun redirectToAuth() {
        Log.d(TAG, "Redirecting to auth activity")
        try {
            startActivity(Intent(this, SimpleAuthActivity::class.java))
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error redirecting to auth", e)
            // Don't finish if we can't redirect
        }
    }
}
