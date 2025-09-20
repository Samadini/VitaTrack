package com.vitatrack.app.ui.exercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vitatrack.app.R
import com.vitatrack.app.data.model.Exercise
import com.vitatrack.app.data.model.ExerciseType
import com.vitatrack.app.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private val onEditClick: (Exercise) -> Unit,
    private val onDeleteClick: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.apply {
                tvExerciseName.text = exercise.name
                tvExerciseType.text = exercise.type.name.lowercase().replaceFirstChar { it.uppercase() }
                tvDuration.text = "${exercise.duration} min"
                tvCalories.text = "${exercise.caloriesBurned} cal"

                // Set icon based on exercise type
                val iconRes = when (exercise.type) {
                    ExerciseType.RUNNING -> R.drawable.ic_running
                    ExerciseType.WALKING -> R.drawable.ic_walking
                    ExerciseType.CYCLING -> R.drawable.ic_cycling
                    ExerciseType.SWIMMING -> R.drawable.ic_swimming
                    ExerciseType.YOGA -> R.drawable.ic_yoga
                    ExerciseType.GYM -> R.drawable.ic_gym
                    else -> R.drawable.ic_exercise
                }
                ivExerciseIcon.setImageResource(iconRes)

                btnEditExercise.setOnClickListener {
                    onEditClick(exercise)
                }

                btnDeleteExercise.setOnClickListener {
                    onDeleteClick(exercise)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem
            }
        }
    }
}
