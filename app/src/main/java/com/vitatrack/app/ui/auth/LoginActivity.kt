package com.vitatrack.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.databinding.ActivityLoginBinding
import com.vitatrack.app.ui.main.MainActivityNew

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    
    companion object {
        private const val TAG = "LoginActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                signInUser(email, password)
            }
        }
        
        binding.tvCreateAccount.setOnClickListener {
            // Navigate to registration page
            startActivity(Intent(this, SimpleAuthActivity::class.java))
            finish()
        }
        
        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google Sign-In coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnApple.setOnClickListener {
            Toast.makeText(this, "Apple Sign-In coming soon!", Toast.LENGTH_SHORT).show()
        }
        
        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordReset(email)
            } else {
                Toast.makeText(this, "Please enter your email first", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Please enter a valid email"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        
        return true
    }
    
    private fun signInUser(email: String, password: String) {
        Log.d(TAG, "Attempting to sign in user: $email")
        
        binding.btnSignIn.isEnabled = false
        binding.btnSignIn.text = "Signing in..."
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.text = "Sign In"
                
                if (task.isSuccessful) {
                    Log.d(TAG, "Sign in successful")
                    Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Log.e(TAG, "Sign in failed", task.exception)
                    val errorMessage = when (task.exception?.message) {
                        "The email address is badly formatted." -> "Please enter a valid email address"
                        "The password is invalid or the user does not have a password." -> "Invalid password"
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> "No account found with this email"
                        else -> "Sign in failed: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivityNew::class.java))
        finish()
    }
}
