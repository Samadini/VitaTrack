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
        
        try {
            binding = ActivityMainNewBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            auth = FirebaseAuth.getInstance()
            
            // Check if user is logged in
            if (auth.currentUser == null) {
                redirectToAuth()
                return
            }
            
            setupNavigation()
            setupToolbar()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            redirectToAuth()
        }
    }
    
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "VitaTrack"
    }
    
    private fun redirectToAuth() {
        startActivity(Intent(this, SimpleAuthActivity::class.java))
        finish()
    }
}
