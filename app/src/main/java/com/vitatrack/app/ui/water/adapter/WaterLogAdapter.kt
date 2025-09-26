package com.vitatrack.app.ui.water.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vitatrack.app.data.model.WaterIntake
import com.vitatrack.app.databinding.ItemWaterLogBinding
import java.text.SimpleDateFormat
import java.util.*

class WaterLogAdapter(
    private val onDeleteClick: (WaterIntake) -> Unit
) : ListAdapter<WaterIntake, WaterLogAdapter.WaterLogViewHolder>(DiffCallback) {

    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterLogViewHolder {
        val binding = ItemWaterLogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WaterLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaterLogViewHolder, position: Int) {
        val waterIntake = getItem(position)
        holder.bind(waterIntake)
    }

    inner class WaterLogViewHolder(
        private val binding: ItemWaterLogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(waterIntake: WaterIntake) {
            binding.apply {
                tvWaterAmount.text = "${waterIntake.amountMl}ml"
                tvWaterTime.text = timeFormat.format(waterIntake.date)

                btnDeleteWater.setOnClickListener {
                    onDeleteClick(waterIntake)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<WaterIntake>() {
            override fun areItemsTheSame(oldItem: WaterIntake, newItem: WaterIntake): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: WaterIntake, newItem: WaterIntake): Boolean {
                return oldItem == newItem
            }
        }
    }
}
