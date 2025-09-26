package com.vitatrack.app.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.databinding.FragmentDashboardBinding
import com.vitatrack.app.ui.dashboard.adapter.RecentActivityAdapter
import com.vitatrack.app.ui.water.WaterTrackingViewModel
import com.vitatrack.app.ui.water.WaterTrackingViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Fragment binding is null")
    
    private val viewModel: DashboardViewModel by viewModels {
        try {
            val app = requireActivity().application as VitaTrackApplication
            DashboardViewModelFactory(
                app.userRepository,
                app.database.mealDao(),
                app.database.waterIntakeDao()
            )
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error creating ViewModel", e)
            throw e
        }
    }
    
    private val waterViewModel: WaterTrackingViewModel by viewModels {
        try {
            val app = requireActivity().application as VitaTrackApplication
            WaterTrackingViewModelFactory(app.database.waterIntakeDao())
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error creating WaterViewModel", e)
            throw e
        }
    }
    
    private lateinit var recentActivityAdapter: RecentActivityAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        try {
            auth = FirebaseAuth.getInstance()
            
            setupUI()
            setupRecyclerView()
            setupWaterTracking()
            setupClickListeners()
            observeViewModels()
            loadDashboardData()
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error in onViewCreated", e)
            // Show error message to user
            Toast.makeText(context, "Error loading dashboard. Please try again.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupUI() {
        // Set welcome message based on time of day
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        val greeting = when (hour) {
            in 0..11 -> "Good Morning!"
            in 12..17 -> "Good Afternoon!"
            else -> "Good Evening!"
        }
        
        binding.tvWelcome.text = greeting
        
        // Set user name
        val currentUser = auth.currentUser
        binding.tvUserName.text = currentUser?.displayName ?: "User"
    }

    private fun setupRecyclerView() {
        try {
            recentActivityAdapter = RecentActivityAdapter { activity ->
                // Handle activity item click
                // TODO: Navigate to activity details
            }
            
            binding.rvRecentActivity.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recentActivityAdapter
            }
            Log.d("DashboardFragment", "RecyclerView setup completed successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error setting up RecyclerView", e)
            // Continue without RecyclerView if it fails
        }
    }

    private fun observeViewModels() {
        try {
            // Observe daily stats
            viewModel.dailyStats.observe(viewLifecycleOwner) { stats ->
                try {
                    updateProgressCards(stats)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Error updating progress cards", e)
                }
            }
            
            // Observe recent activities
            viewModel.recentActivities.observe(viewLifecycleOwner) { activities ->
                try {
                    if (::recentActivityAdapter.isInitialized) {
                        recentActivityAdapter.submitList(activities)
                    }
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Error updating recent activities", e)
                }
            }
            
            // Observe water tracking
            waterViewModel.dailyWaterIntake.observe(viewLifecycleOwner) { intake ->
                try {
                    updateWaterProgress(intake)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Error updating water progress", e)
                }
            }
            
            waterViewModel.dailyGoal.observe(viewLifecycleOwner) { goal ->
                try {
                    updateWaterGoal(goal)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Error updating water goal", e)
                }
            }
            
            Log.d("DashboardFragment", "ViewModels observers setup completed successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error setting up ViewModel observers", e)
        }
    }
    
    private fun updateWaterProgress(intake: Int) {
        // Update water progress in UI - using safe calls since layout may vary
        try {
            view?.findViewById<android.widget.TextView>(R.id.tvWaterAmount)?.text = "${intake} ml"
            
            val percentage = waterViewModel.getProgressPercentage()
            view?.findViewById<android.widget.ProgressBar>(R.id.progressWater)?.progress = percentage
            
            // Log the progress for now since UI elements may not exist
            Log.d("DashboardFragment", "Water progress: ${intake}ml ($percentage%)")
        } catch (e: Exception) {
            Log.d("DashboardFragment", "Water UI elements not found in current layout")
        }
    }
    
    private fun updateWaterGoal(goal: Int) {
        // Update water goal in UI - using safe calls since layout may vary
        try {
            // Log the goal for now since UI elements may not exist
            Log.d("DashboardFragment", "Water goal: ${goal}ml")
        } catch (e: Exception) {
            Log.d("DashboardFragment", "Water goal UI elements not found in current layout")
        }
    }

    private fun setupWaterTracking() {
        // Check if the new layout has water tracking buttons
        try {
            val add250Button = view?.findViewById<View>(R.id.btnAdd250ml)
            val add500Button = view?.findViewById<View>(R.id.btnAdd500ml)
            
            add250Button?.setOnClickListener {
                addWaterIntake(250)
            }
            
            add500Button?.setOnClickListener {
                addWaterIntake(500)
            }
        } catch (e: Exception) {
            // Buttons not found in current layout
        }
    }
    
    private fun addWaterIntake(amount: Int) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            waterViewModel.addWaterIntake(currentUser.uid, amount)
            Toast.makeText(context, "Added ${amount}ml water!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupClickListeners() {
        try {
            binding.btnAddExercise?.setOnClickListener {
                // Exercise functionality temporarily disabled
                Toast.makeText(context, "Exercise tracking coming soon!", Toast.LENGTH_SHORT).show()
            }
            
            binding.btnAddMeal?.setOnClickListener {
                // Meal functionality coming soon
                Toast.makeText(context, "Meal tracking coming soon!", Toast.LENGTH_SHORT).show()
            }
            
            binding.btnAddWater?.setOnClickListener {
                // Water functionality coming soon
                Toast.makeText(context, "Use the +250ml and +500ml buttons!", Toast.LENGTH_SHORT).show()
            }
            
            binding.tvViewAll?.setOnClickListener {
                // Navigate to insights for now
                findNavController().navigate(R.id.insightsFragment)
            }
        } catch (e: Exception) {
            // Some buttons might not exist in current layout
        }
    }

    private fun loadDashboardData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModel.loadDashboardData(currentUser.uid)
            waterViewModel.loadTodayWaterIntake(currentUser.uid)
        }
    }

    private fun updateProgressCards(stats: DailyStats) {
        try {
            // Update steps
            binding.tvStepsCount.text = stats.steps.toString()
            binding.progressSteps.progress = stats.steps
            
            // Update calories
            binding.tvCaloriesCount.text = stats.calories.toString()
            binding.progressCalories.progress = stats.calories
            
            // Update water (convert ml to liters)
            val waterInLiters = stats.waterIntake / 1000f
            binding.tvWaterCount.text = String.format("%.1fL", waterInLiters)
            binding.progressWater.progress = stats.waterIntake
            
            // Update exercise duration
            binding.tvExerciseCount.text = "${stats.exerciseDuration} min"
            binding.progressExercise.progress = stats.exerciseDuration
            
            Log.d("DashboardFragment", "Progress cards updated successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error updating progress cards", e)
            // Show fallback data or continue without updating
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
