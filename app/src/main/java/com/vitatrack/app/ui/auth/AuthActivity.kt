package com.vitatrack.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.vitatrack.app.R
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.model.User
import com.vitatrack.app.databinding.ActivityAuthBinding
import com.vitatrack.app.ui.main.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class AuthActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isLoginMode = true
    
    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "AuthActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeFirebase()
        setupGoogleSignIn()
        setupClickListeners()
        updateUIForMode()
    }
    
    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }
    
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    
    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            if (isLoginMode) {
                signInWithEmail()
            } else {
                signUpWithEmail()
            }
        }
        
        binding.tvCreateAccount.setOnClickListener {
            toggleMode()
        }
        
        binding.btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }
        
        binding.tvForgotPassword.setOnClickListener {
            resetPassword()
        }
        
        binding.tvContactSupport.setOnClickListener {
            // Handle support contact
            Toast.makeText(this, "Contact support feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun toggleMode() {
        isLoginMode = !isLoginMode
        updateUIForMode()
    }
    
    private fun updateUIForMode() {
        if (isLoginMode) {
            binding.tilUsername.visibility = View.GONE
            binding.btnSignIn.text = getString(R.string.sign_in)
            binding.tvCreateAccount.text = getString(R.string.create_new_account)
        } else {
            binding.tilUsername.visibility = View.VISIBLE
            binding.btnSignIn.text = getString(R.string.sign_up)
            binding.tvCreateAccount.text = getString(R.string.sign_in)
        }
    }
    
    private fun signInWithEmail() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (!validateInput(email, password)) return
        
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                navigateToMain()
            } catch (e: Exception) {
                Log.e(TAG, "Sign in failed", e)
                Toast.makeText(this@AuthActivity, "Sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }
    
    private fun signUpWithEmail() {
        val username = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (!validateInput(email, password, username)) return
        
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                
                if (firebaseUser != null) {
                    // Create user profile
                    val user = User(
                        uid = firebaseUser.uid,
                        email = email,
                        displayName = username,
                        createdAt = Date(),
                        updatedAt = Date()
                    )
                    
                    // Save to Firestore
                    firestore.collection("users")
                        .document(firebaseUser.uid)
                        .set(user)
                        .await()
                    
                    // Save to local database
                    val app = application as VitaTrackApplication
                    app.userRepository.insertUser(user)
                    
                    navigateToMain()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Sign up failed", e)
                Toast.makeText(this@AuthActivity, "Sign up failed: ${e.message}", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }
    
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun firebaseAuthWithGoogle(idToken: String) {
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val result = auth.signInWithCredential(credential).await()
                val firebaseUser = result.user
                
                if (firebaseUser != null) {
                    // Check if user exists in Firestore
                    val userDoc = firestore.collection("users")
                        .document(firebaseUser.uid)
                        .get()
                        .await()
                    
                    if (!userDoc.exists()) {
                        // Create new user profile
                        val user = User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email ?: "",
                            displayName = firebaseUser.displayName ?: "",
                            photoUrl = firebaseUser.photoUrl?.toString(),
                            createdAt = Date(),
                            updatedAt = Date()
                        )
                        
                        // Save to Firestore
                        firestore.collection("users")
                            .document(firebaseUser.uid)
                            .set(user)
                            .await()
                        
                        // Save to local database
                        val app = application as VitaTrackApplication
                        app.userRepository.insertUser(user)
                    }
                    
                    navigateToMain()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Google auth failed", e)
                Toast.makeText(this@AuthActivity, "Authentication failed: ${e.message}", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }
    
    private fun resetPassword() {
        val email = binding.etEmail.text.toString().trim()
        
        if (email.isEmpty()) {
            binding.tilEmail.error = "Please enter your email"
            return
        }
        
        showLoading(true)
        
        lifecycleScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
                Toast.makeText(this@AuthActivity, "Password reset email sent", Toast.LENGTH_LONG).show()
                showLoading(false)
            } catch (e: Exception) {
                Log.e(TAG, "Password reset failed", e)
                Toast.makeText(this@AuthActivity, "Failed to send reset email: ${e.message}", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }
    
    private fun validateInput(email: String, password: String, username: String? = null): Boolean {
        var isValid = true
        
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }
        
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }
        
        if (!isLoginMode && username.isNullOrEmpty()) {
            binding.tilUsername.error = "Username is required"
            isValid = false
        } else {
            binding.tilUsername.error = null
        }
        
        return isValid
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingOverlay.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnSignIn.isEnabled = !show
        binding.btnGoogleSignIn.isEnabled = !show
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
