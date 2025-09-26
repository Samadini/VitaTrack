package com.vitatrack.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.ui.auth.SimpleAuthActivity

class SimpleMainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "SimpleMainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SimpleMainActivity onCreate started")
        
        try {
            setContentView(R.layout.activity_simple_main)
            Log.d(TAG, "Layout set successfully")
            
            setupViews()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
        }
    }
    
    private fun setupViews() {
        try {
            val welcomeText = findViewById<TextView>(R.id.tvWelcome)
            val logoutButton = findViewById<Button>(R.id.btnLogout)
            
            // Get current user info
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val userEmail = currentUser?.email ?: "Unknown User"
            
            welcomeText?.text = "Welcome to VitaTrack!\n\nLogged in as: $userEmail\n\nApp is now working successfully! 🎉"
            
            logoutButton?.setOnClickListener {
                logout()
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up views", e)
        }
    }
    
    private fun logout() {
        try {
            FirebaseAuth.getInstance().signOut()
            Log.d(TAG, "User logged out")
            
            startActivity(Intent(this, SimpleAuthActivity::class.java))
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error during logout", e)
        }
    }
}
