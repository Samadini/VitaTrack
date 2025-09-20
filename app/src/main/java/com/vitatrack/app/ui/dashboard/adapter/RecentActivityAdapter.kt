package com.vitatrack.app.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vitatrack.app.R
import com.vitatrack.app.databinding.ItemRecentActivityBinding
import com.vitatrack.app.ui.dashboard.ActivityType
import com.vitatrack.app.ui.dashboard.RecentActivity

class RecentActivityAdapter(
    private val onItemClick: (RecentActivity) -> Unit
) : ListAdapter<RecentActivity, RecentActivityAdapter.RecentActivityViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        val binding = ItemRecentActivityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        val activity = getItem(position)
        holder.bind(activity)
    }

    inner class RecentActivityViewHolder(
        private val binding: ItemRecentActivityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: RecentActivity) {
            binding.apply {
                tvActivityTitle.text = activity.title
                tvActivityDetails.text = activity.details
                tvActivityTime.text = activity.time

                // Set icon based on activity type
                val iconRes = when (activity.type) {
                    ActivityType.EXERCISE -> R.drawable.ic_exercise
                    ActivityType.MEAL -> R.drawable.ic_meal
                    ActivityType.WATER -> R.drawable.ic_water
                }
                ivActivityIcon.setImageResource(iconRes)

                root.setOnClickListener {
                    onItemClick(activity)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<RecentActivity>() {
            override fun areItemsTheSame(oldItem: RecentActivity, newItem: RecentActivity): Boolean {
                return oldItem.title == newItem.title && oldItem.time == newItem.time
            }

            override fun areContentsTheSame(oldItem: RecentActivity, newItem: RecentActivity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
