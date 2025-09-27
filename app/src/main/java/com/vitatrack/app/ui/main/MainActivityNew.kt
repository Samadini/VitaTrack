package com.vitatrack.app.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.databinding.ActivityMainNewBinding
import com.vitatrack.app.ui.auth.LoginActivity

class MainActivityNew : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainNewBinding
    private lateinit var auth: FirebaseAuth
    
    companion object {
        private const val TAG = "MainActivityNew"
        private const val PREFS_NAME = "VitaTrackPrefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivityNew onCreate started")
        
        // Initialize binding and auth first
        binding = ActivityMainNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        
        // Check if user is logged in using SharedPreferences
        if (!isUserLoggedIn()) {
            Log.d(TAG, "No user logged in, redirecting to login")
            redirectToAuth()
            return
        }
        
        val userEmail = getUserEmail()
        Log.d(TAG, "User is logged in: $userEmail")
        
        // Setup components with individual error handling
        setupNavigation()
        setupToolbar()
        
        Log.d(TAG, "MainActivityNew onCreate completed successfully")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivityNew onResume")
        
        // Double-check user is still logged in
        if (!isUserLoggedIn()) {
            Log.w(TAG, "User session lost, redirecting to login")
            redirectToAuth()
        } else {
            Log.d(TAG, "User session valid: ${getUserEmail()}")
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
                    Log.e(TAG, "Error in navigation setup post block", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up navigation", e)
        }
    }
    
    private fun setupToolbar() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = "VitaTrack"
            
            // Make toolbar title clickable to navigate home
            binding.toolbar.setOnClickListener {
                navigateToHome()
            }
            
            Log.d(TAG, "Toolbar setup completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up toolbar", e)
            // Toolbar setup failure is not critical, continue without crashing
        }
    }
    
    private fun navigateToHome() {
        try {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            val navController = navHostFragment?.navController
            
            navController?.navigate(R.id.nav_dashboard)
            Log.d(TAG, "Navigated to home")
        } catch (e: Exception) {
            Log.e(TAG, "Error navigating to home", e)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun isUserLoggedIn(): Boolean {
        return try {
            val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val isLoggedIn = sharedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)
            val userEmail = sharedPrefs.getString(KEY_USER_EMAIL, "")
            isLoggedIn && !userEmail.isNullOrEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking login status", e)
            false
        }
    }
    
    private fun getUserEmail(): String {
        return try {
            val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            sharedPrefs.getString(KEY_USER_EMAIL, "") ?: ""
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user email", e)
            ""
        }
    }
    
    private fun logout() {
        Log.d(TAG, "Logging out user")
        try {
            // Clear SharedPreferences
            val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putBoolean(KEY_IS_LOGGED_IN, false)
            editor.remove(KEY_USER_EMAIL)
            editor.apply()
            
            // Sign out from Firebase as well
            auth.signOut()
            
            // Redirect to login
            redirectToAuth()
        } catch (e: Exception) {
            Log.e(TAG, "Error during logout", e)
        }
    }
    
    private fun redirectToAuth() {
        Log.d(TAG, "Redirecting to login activity")
        try {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error redirecting to login", e)
            // Don't finish if we can't redirect
        }
    }
}
