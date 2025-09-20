package com.vitatrack.app.ui.exercise

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vitatrack.app.R
import com.vitatrack.app.data.model.Exercise
import com.vitatrack.app.data.model.ExerciseType
import com.vitatrack.app.databinding.DialogAddEditExerciseBinding
import java.util.*

class AddEditExerciseDialog : DialogFragment() {

    private var _binding: DialogAddEditExerciseBinding? = null
    private val binding get() = _binding!!
    
    private var exercise: Exercise? = null
    private var onExerciseSavedListener: ((Exercise) -> Unit)? = null

    companion object {
        private const val ARG_EXERCISE = "exercise"
        
        fun newInstance(exercise: Exercise? = null): AddEditExerciseDialog {
            val dialog = AddEditExerciseDialog()
            val args = Bundle()
            exercise?.let { args.putParcelable(ARG_EXERCISE, it) }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = arguments?.getParcelable(ARG_EXERCISE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddEditExerciseBinding.inflate(layoutInflater)
        
        setupUI()
        setupClickListeners()
        
        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun setupUI() {
        // Set title
        binding.tvDialogTitle.text = if (exercise == null) {
            getString(R.string.add_exercise)
        } else {
            getString(R.string.edit) + " Exercise"
        }

        // Setup exercise type dropdown
        val exerciseTypes = ExerciseType.values().map { 
            it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, exerciseTypes)
        binding.actvExerciseType.setAdapter(adapter)

        // Fill existing data if editing
        exercise?.let { ex ->
            binding.etExerciseName.setText(ex.name)
            binding.actvExerciseType.setText(ex.type.name.lowercase().replaceFirstChar { it.uppercase() }, false)
            binding.etDuration.setText(ex.duration.toString())
            binding.etCaloriesBurned.setText(ex.caloriesBurned.toString())
            binding.etNotes.setText(ex.notes ?: "")
        }
    }

    private fun setupClickListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            if (validateInput()) {
                saveExercise()
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val name = binding.etExerciseName.text.toString().trim()
        if (name.isEmpty()) {
            binding.tilExerciseName.error = "Exercise name is required"
            isValid = false
        } else {
            binding.tilExerciseName.error = null
        }

        val typeText = binding.actvExerciseType.text.toString().trim()
        if (typeText.isEmpty()) {
            binding.tilExerciseType.error = "Exercise type is required"
            isValid = false
        } else {
            binding.tilExerciseType.error = null
        }

        val durationText = binding.etDuration.text.toString().trim()
        if (durationText.isEmpty()) {
            binding.tilDuration.error = "Duration is required"
            isValid = false
        } else {
            try {
                val duration = durationText.toInt()
                if (duration <= 0) {
                    binding.tilDuration.error = "Duration must be greater than 0"
                    isValid = false
                } else {
                    binding.tilDuration.error = null
                }
            } catch (e: NumberFormatException) {
                binding.tilDuration.error = "Invalid duration"
                isValid = false
            }
        }

        val caloriesText = binding.etCaloriesBurned.text.toString().trim()
        if (caloriesText.isEmpty()) {
            binding.tilCaloriesBurned.error = "Calories burned is required"
            isValid = false
        } else {
            try {
                val calories = caloriesText.toInt()
                if (calories < 0) {
                    binding.tilCaloriesBurned.error = "Calories cannot be negative"
                    isValid = false
                } else {
                    binding.tilCaloriesBurned.error = null
                }
            } catch (e: NumberFormatException) {
                binding.tilCaloriesBurned.error = "Invalid calories"
                isValid = false
            }
        }

        return isValid
    }

    private fun saveExercise() {
        val name = binding.etExerciseName.text.toString().trim()
        val typeText = binding.actvExerciseType.text.toString().trim()
        val duration = binding.etDuration.text.toString().toInt()
        val calories = binding.etCaloriesBurned.text.toString().toInt()
        val notes = binding.etNotes.text.toString().trim().ifEmpty { null }

        val exerciseType = ExerciseType.values().find { 
            it.name.lowercase().replaceFirstChar { char -> char.uppercase() } == typeText 
        } ?: ExerciseType.OTHER

        val savedExercise = if (exercise == null) {
            Exercise(
                userId = "", // Will be set in fragment
                type = exerciseType,
                name = name,
                duration = duration,
                caloriesBurned = calories,
                notes = notes,
                date = Date()
            )
        } else {
            exercise!!.copy(
                name = name,
                type = exerciseType,
                duration = duration,
                caloriesBurned = calories,
                notes = notes
            )
        }

        onExerciseSavedListener?.invoke(savedExercise)
        dismiss()
    }

    fun setOnExerciseSavedListener(listener: (Exercise) -> Unit) {
        onExerciseSavedListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
