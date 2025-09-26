package com.vitatrack.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.model.User
import com.vitatrack.app.ui.main.MainActivityNew
import kotlinx.coroutines.launch
import java.util.Date

class SimpleAuthActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    
    companion object {
        private const val TAG = "SimpleAuthActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SimpleAuthActivity onCreate started")
        
        try {
            setContentView(R.layout.activity_simple_auth)
            Log.d(TAG, "Layout set successfully")
            
            auth = FirebaseAuth.getInstance()
            setupViews()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
        }
    }
    
    private fun setupViews() {
        try {
            val fullNameField = findViewById<EditText>(R.id.etFullName)
            val emailField = findViewById<EditText>(R.id.etEmail)
            val passwordField = findViewById<EditText>(R.id.etPassword)
            val confirmPasswordField = findViewById<EditText>(R.id.etConfirmPassword)
            val createAccountButton = findViewById<Button>(R.id.btnCreateAccount)
            val termsCheckbox = findViewById<CheckBox>(R.id.cbTerms)
            val signInLink = findViewById<TextView>(R.id.tvSignIn)
            val googleButton = findViewById<Button>(R.id.btnGoogle)
            val appleButton = findViewById<Button>(R.id.btnApple)
            
            createAccountButton?.setOnClickListener {
                val fullName = fullNameField?.text.toString().trim()
                val email = emailField?.text.toString().trim()
                val password = passwordField?.text.toString().trim()
                val confirmPassword = confirmPasswordField?.text.toString().trim()
                val termsAccepted = termsCheckbox?.isChecked ?: false
                
                if (validateInput(fullName, email, password, confirmPassword, termsAccepted)) {
                    createAccount(email, password)
                }
            }
            
            signInLink?.setOnClickListener {
                // Switch to sign in mode (for now, just try to sign in with existing credentials)
                val email = emailField?.text.toString().trim()
                val password = passwordField?.text.toString().trim()
                
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signInUser(email, password)
                } else {
                    Toast.makeText(this, "Please enter email and password to sign in", Toast.LENGTH_SHORT).show()
                }
            }
            
            googleButton?.setOnClickListener {
                Toast.makeText(this, "Google Sign-In coming soon!", Toast.LENGTH_SHORT).show()
            }
            
            appleButton?.setOnClickListener {
                Toast.makeText(this, "Apple Sign-In coming soon!", Toast.LENGTH_SHORT).show()
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up views", e)
        }
    }
    
    private fun validateInput(fullName: String, email: String, password: String, confirmPassword: String, termsAccepted: Boolean): Boolean {
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (!termsAccepted) {
            Toast.makeText(this, "Please accept the Terms and Privacy Policy", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }
    
    private fun signInUser(email: String, password: String) {
        Log.d(TAG, "Attempting to sign in user")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Sign in successful")
                    Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Log.e(TAG, "Sign in failed", task.exception)
                    Toast.makeText(this, "Sign in failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "Attempting to create account")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Account creation successful")
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        saveUserToDatabase(firebaseUser.uid, email)
                    }
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Log.e(TAG, "Account creation failed", task.exception)
                    Toast.makeText(this, "Account creation failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun saveUserToDatabase(userId: String, email: String) {
        lifecycleScope.launch {
            try {
                val fullNameField = findViewById<EditText>(R.id.etFullName)
                val fullName = fullNameField?.text.toString().trim()
                
                val user = User(
                    id = userId,
                    name = fullName,
                    email = email,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                
                val userRepository = (application as VitaTrackApplication).userRepository
                userRepository.insertUser(user)
                Log.d(TAG, "User saved to database successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error saving user to database", e)
            }
        }
    }
    
    private fun navigateToMain() {
        try {
            startActivity(Intent(this, MainActivityNew::class.java))
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to navigate to MainActivityNew", e)
        }
    }
}
