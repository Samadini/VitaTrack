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
    private val binding get() = _binding!!
    
    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(
            (requireActivity().application as VitaTrackApplication).userRepository,
            (requireActivity().application as VitaTrackApplication).database.mealDao(),
            (requireActivity().application as VitaTrackApplication).database.waterIntakeDao()
        )
    }
    
    private val waterViewModel: WaterTrackingViewModel by viewModels {
        WaterTrackingViewModelFactory(
            (requireActivity().application as VitaTrackApplication).database.waterIntakeDao()
        )
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
        
        auth = FirebaseAuth.getInstance()
        
        setupWaterTracking()
        setupClickListeners()
        observeViewModels()
        loadDashboardData()
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
        recentActivityAdapter = RecentActivityAdapter { activity ->
            // Handle activity item click
            // TODO: Navigate to activity details
        }
        
        binding.rvRecentActivity.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentActivityAdapter
        }
    }

    private fun observeViewModels() {
        // Observe daily stats
        viewModel.dailyStats.observe(viewLifecycleOwner) { stats ->
            updateProgressCards(stats)
        }
        
        // Observe recent activities
        viewModel.recentActivities.observe(viewLifecycleOwner) { activities ->
            recentActivityAdapter.submitList(activities)
        }
        
        // Observe water tracking
        waterViewModel.dailyWaterIntake.observe(viewLifecycleOwner) { intake ->
            updateWaterProgress(intake)
        }
        
        waterViewModel.dailyGoal.observe(viewLifecycleOwner) { goal ->
            updateWaterGoal(goal)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
