package com.vitatrack.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(
            (requireActivity().application as VitaTrackApplication).userRepository,
            (requireActivity().application as VitaTrackApplication).database.exerciseDao(),
            (requireActivity().application as VitaTrackApplication).database.mealDao(),
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
        
        setupUI()
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
        
        // Load data
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

    private fun observeViewModel() {
        // Observe daily stats
        viewModel.dailyStats.observe(viewLifecycleOwner) { stats ->
            updateProgressCards(stats)
        }
        
        // Observe recent activities
        viewModel.recentActivities.observe(viewLifecycleOwner) { activities ->
            recentActivityAdapter.submitList(activities)
        }
    }

    private fun setupClickListeners() {
        binding.btnAddExercise.setOnClickListener {
            findNavController().navigate(R.id.exerciseFragment)
        }
        
        binding.btnAddMeal.setOnClickListener {
            findNavController().navigate(R.id.mealFragment)
        }
        
        binding.btnAddWater.setOnClickListener {
            findNavController().navigate(R.id.waterFragment)
        }
        
        binding.tvViewAll.setOnClickListener {
            findNavController().navigate(R.id.progressFragment)
        }
    }

    private fun loadDashboardData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModel.loadDashboardData(currentUser.uid)
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
