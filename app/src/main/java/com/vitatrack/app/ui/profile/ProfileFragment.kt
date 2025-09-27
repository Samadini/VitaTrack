package com.vitatrack.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.ui.auth.LoginActivity
import com.vitatrack.app.ui.base.BaseFragment

class ProfileFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        val textView = view.findViewById<TextView>(R.id.tvProfileTitle)
        val logoutButton = view.findViewById<Button>(R.id.btnLogout)
        
        // Get current user info
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email ?: "Unknown User"
        
        textView?.text = "👤 Profile\n\nLogged in as:\n$userEmail\n\nManage your account settings here."
        
        logoutButton?.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        
        return view
    }
}
