package com.vitatrack.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class SimpleTestApplication : Application() {

    companion object {
        private const val TAG = "SimpleTestApplication"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SimpleTestApplication onCreate started")
        
        try {
            // Initialize Firebase
            FirebaseApp.initializeApp(this)
            Log.d(TAG, "Firebase initialized successfully")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error during initialization", e)
        }
        
        Log.d(TAG, "SimpleTestApplication onCreate completed")
    }
}
