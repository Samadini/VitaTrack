package com.vitatrack.app.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.model.Exercise
import com.vitatrack.app.databinding.FragmentExerciseBinding
import com.vitatrack.app.ui.exercise.adapter.ExerciseAdapter

class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ExerciseViewModel by viewModels {
        ExerciseViewModelFactory(
            (requireActivity().application as VitaTrackApplication).database.exerciseDao()
        )
    }
    
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
        
        // Load exercises
        loadExercises()
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(
            onEditClick = { exercise ->
                showAddEditExerciseDialog(exercise)
            },
            onDeleteClick = { exercise ->
                showDeleteConfirmationDialog(exercise)
            }
        )
        
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.submitList(exercises)
            updateUI(exercises)
        }
        
        viewModel.todayExerciseDuration.observe(viewLifecycleOwner) { duration ->
            binding.tvTodayExercise.text = "Today: $duration minutes"
        }
    }

    private fun setupClickListeners() {
        binding.fabAddExercise.setOnClickListener {
            showAddEditExerciseDialog()
        }
        
        binding.btnAddFirstExercise.setOnClickListener {
            showAddEditExerciseDialog()
        }
    }

    private fun loadExercises() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModel.loadExercises(currentUser.uid)
        }
    }

    private fun updateUI(exercises: List<Exercise>) {
        if (exercises.isEmpty()) {
            binding.rvExercises.visibility = View.GONE
            binding.emptyState.visibility = View.VISIBLE
        } else {
            binding.rvExercises.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
        }
    }

    private fun showAddEditExerciseDialog(exercise: Exercise? = null) {
        val dialog = AddEditExerciseDialog.newInstance(exercise)
        dialog.setOnExerciseSavedListener { savedExercise ->
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val exerciseWithUserId = savedExercise.copy(userId = currentUser.uid)
                if (exercise == null) {
                    viewModel.addExercise(exerciseWithUserId)
                } else {
                    viewModel.updateExercise(exerciseWithUserId)
                }
            }
        }
        dialog.show(parentFragmentManager, "AddEditExerciseDialog")
    }

    private fun showDeleteConfirmationDialog(exercise: Exercise) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Exercise")
            .setMessage("Are you sure you want to delete this exercise?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteExercise(exercise)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
