package com.vitatrack.app.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.vitatrack.app.R

object FirebaseConfigValidator {

    private const val TAG = "FirebaseConfigValidator"

    /**
     * Performs lightweight runtime checks to help diagnose common Firebase configuration issues.
     * Intended for debug builds. Safe to call in release as it only logs and shows a short toast.
     */
    fun validate(context: Context) {
        try {
            // 1) Check FirebaseApp initialization
            val app = FirebaseApp.getInstance()
            val opts = app.options
            
            // Log basic Firebase config (safely)
            val safeKey = opts.apiKey?.take(8) ?: "<empty>"
            Log.d(TAG, "Firebase initialized. Project: ${opts.projectId}, App: ${opts.applicationId}, API Key: $safeKey...")
            
            // 2) Check for Google Play Services (non-blocking, just log)
            try {
                val gpStatus = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                if (gpStatus != ConnectionResult.SUCCESS) {
                    Log.w(TAG, "Google Play Services issue: ${GoogleApiAvailability.getInstance().getErrorString(gpStatus)}")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Couldn't check Google Play Services", e)
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Firebase validation failed. Check your google-services.json and Firebase setup.", e)
            // Don't show toast here to avoid blocking app startup
        }
    }
}
