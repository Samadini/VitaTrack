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
    
    // Lazy initialization to avoid crashes during fragment creation
    private val viewModel: DashboardViewModel by lazy {
        try {
            val app = requireActivity().application as VitaTrackApplication
            val factory = DashboardViewModelFactory(
                app.userRepository,
                app.database.mealDao(),
                app.database.waterIntakeDao()
            )
            androidx.lifecycle.ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error creating DashboardViewModel", e)
            // Create a basic ViewModel without factory as fallback
            androidx.lifecycle.ViewModelProvider(this)[DashboardViewModel::class.java]
        }
    }
    
    private val waterViewModel: WaterTrackingViewModel by lazy {
        try {
            val app = requireActivity().application as VitaTrackApplication
            val factory = WaterTrackingViewModelFactory(app.database.waterIntakeDao())
            androidx.lifecycle.ViewModelProvider(this, factory)[WaterTrackingViewModel::class.java]
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error creating WaterViewModel", e)
            // Create a basic ViewModel without factory as fallback
            androidx.lifecycle.ViewModelProvider(this)[WaterTrackingViewModel::class.java]
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
        
        Log.d("DashboardFragment", "onViewCreated started")
        
        try {
            // Initialize Firebase Auth
            auth = FirebaseAuth.getInstance()
            Log.d("DashboardFragment", "Firebase Auth initialized")
            
            // Setup UI components step by step with individual error handling
            setupUI()
            setupRecyclerView()
            setupWaterTracking()
            setupClickListeners()
            
            // Initialize ViewModels and observe data
            initializeViewModels()
            observeViewModels()
            loadDashboardData()
            
            Log.d("DashboardFragment", "onViewCreated completed successfully")
            
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Critical error in onViewCreated", e)
            // Show error message to user but don't crash
            try {
                Toast.makeText(context, "Dashboard loaded with limited functionality", Toast.LENGTH_LONG).show()
            } catch (toastError: Exception) {
                Log.e("DashboardFragment", "Even toast failed", toastError)
            }
        }
    }
    
    private fun initializeViewModels() {
        try {
            // Access ViewModels to trigger initialization
            Log.d("DashboardFragment", "Initializing ViewModels...")
            val vm = viewModel // This triggers lazy initialization
            val wvm = waterViewModel // This triggers lazy initialization
            Log.d("DashboardFragment", "ViewModels initialized successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error initializing ViewModels", e)
            // Continue without ViewModels if necessary
        }
    }

    private fun setupUI() {
        try {
            // Set welcome message based on time of day
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            
            val greeting = when (hour) {
                in 0..11 -> "Good Morning!"
                in 12..17 -> "Good Afternoon!"
                else -> "Good Evening!"
            }
            
            _binding?.tvWelcome?.text = greeting
            
            // Set user name
            val currentUser = auth.currentUser
            _binding?.tvUserName?.text = currentUser?.displayName ?: "User"
            
            Log.d("DashboardFragment", "UI setup completed successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error setting up UI", e)
        }
    }

    private fun setupRecyclerView() {
        try {
            recentActivityAdapter = RecentActivityAdapter { activity ->
                // Handle activity item click
                // TODO: Navigate to activity details
            }
            
            _binding?.rvRecentActivity?.apply {
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
            _binding?.btnAddExercise?.setOnClickListener {
                // Exercise functionality temporarily disabled
                Toast.makeText(context, "Exercise tracking coming soon!", Toast.LENGTH_SHORT).show()
            }
            
            _binding?.btnAddMeal?.setOnClickListener {
                // Meal functionality coming soon
                Toast.makeText(context, "Meal tracking coming soon!", Toast.LENGTH_SHORT).show()
            }
            
            _binding?.btnAddWater?.setOnClickListener {
                // Navigate to Log Water Fragment
                try {
                    findNavController().navigate(R.id.action_dashboardFragment_to_logWaterFragment)
                    Log.d("DashboardFragment", "Navigating to Log Water Fragment")
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Error navigating to Log Water Fragment", e)
                    Toast.makeText(context, "Error opening water logging", Toast.LENGTH_SHORT).show()
                }
            }
            
            _binding?.tvViewAll?.setOnClickListener {
                // Navigate to insights for now
                try {
                    findNavController().navigate(R.id.insightsFragment)
                } catch (e: Exception) {
                    Log.e("DashboardFragment", "Navigation error", e)
                }
            }
            
            Log.d("DashboardFragment", "Click listeners setup completed successfully")
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Error setting up click listeners", e)
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
            _binding?.tvStepsCount?.text = stats.steps.toString()
            _binding?.progressSteps?.progress = stats.steps
            
            // Update calories
            _binding?.tvCaloriesCount?.text = stats.calories.toString()
            _binding?.progressCalories?.progress = stats.calories
            
            // Update water (convert ml to liters)
            val waterInLiters = stats.waterIntake / 1000f
            _binding?.tvWaterCount?.text = String.format("%.1fL", waterInLiters)
            _binding?.progressWater?.progress = stats.waterIntake
            
            // Update exercise duration
            _binding?.tvExerciseCount?.text = "${stats.exerciseDuration} min"
            _binding?.progressExercise?.progress = stats.exerciseDuration
            
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
