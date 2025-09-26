package com.vitatrack.app.ui.water

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.model.WaterIntake
import com.vitatrack.app.databinding.FragmentWaterBinding
import com.vitatrack.app.ui.water.adapter.WaterLogAdapter
import java.util.*

class WaterFragment : Fragment() {

    private var _binding: FragmentWaterBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: WaterViewModel by viewModels {
        WaterViewModelFactory(
            (requireActivity().application as VitaTrackApplication).database.waterIntakeDao()
        )
    }
    
    private lateinit var waterLogAdapter: WaterLogAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
        
        // Load water data
        loadWaterData()
    }

    private fun setupRecyclerView() {
        waterLogAdapter = WaterLogAdapter { waterIntake ->
            showDeleteConfirmationDialog(waterIntake)
        }
        
        binding.rvWaterLog.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = waterLogAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.waterIntakes.observe(viewLifecycleOwner) { waterIntakes ->
            waterLogAdapter.submitList(waterIntakes)
            updateEmptyState(waterIntakes)
        }
        
        viewModel.dailyTotal.observe(viewLifecycleOwner) { total ->
            updateProgressUI(total)
        }
    }

    private fun setupClickListeners() {
        binding.btnAdd250ml.setOnClickListener {
            addWaterIntake(250)
        }
        
        binding.btnAdd500ml.setOnClickListener {
            addWaterIntake(500)
        }
        
        binding.btnAdd750ml.setOnClickListener {
            addWaterIntake(750)
        }
        
        binding.btnCustomAmount.setOnClickListener {
            showCustomAmountDialog()
        }
    }

    private fun loadWaterData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModel.loadWaterData(currentUser.uid)
        }
    }

    private fun addWaterIntake(amount: Int) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val waterIntake = WaterIntake(
                userId = currentUser.uid,
                amountMl = amount,
                date = Date()
            )
            viewModel.addWaterIntake(waterIntake)
        }
    }

    private fun showCustomAmountDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "Enter amount in ml"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Custom Amount")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val amountText = editText.text.toString()
                if (amountText.isNotEmpty()) {
                    try {
                        val amount = amountText.toInt()
                        if (amount > 0) {
                            addWaterIntake(amount)
                        }
                    } catch (e: NumberFormatException) {
                        // Handle invalid input
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog(waterIntake: WaterIntake) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Water Log")
            .setMessage("Are you sure you want to delete this water intake entry?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteWaterIntake(waterIntake)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateProgressUI(totalMl: Int) {
        val goalMl = 2000 // Default goal
        val progressPercentage = (totalMl.toFloat() / goalMl * 100).toInt()
        
        // Update progress bar
        binding.circularProgress.progress = totalMl
        binding.circularProgress.max = goalMl
        
        // Update text
        val totalLiters = totalMl / 1000f
        val goalLiters = goalMl / 1000f
        
        binding.tvCurrentIntake.text = String.format("%.1fL", totalLiters)
        binding.tvGoal.text = "of ${String.format("%.1fL", goalLiters)} goal"
        binding.tvProgressText.text = "$progressPercentage% of daily goal achieved"
    }

    private fun updateEmptyState(waterIntakes: List<WaterIntake>) {
        if (waterIntakes.isEmpty()) {
            binding.rvWaterLog.visibility = View.GONE
            binding.emptyStateWater.visibility = View.VISIBLE
        } else {
            binding.rvWaterLog.visibility = View.VISIBLE
            binding.emptyStateWater.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
