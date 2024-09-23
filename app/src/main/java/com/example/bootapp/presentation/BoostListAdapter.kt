package com.example.bootapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bootapp.databinding.ItemBootBinding
import com.example.bootapp.domain.BootDayGroupModel

class BoostListAdapter : ListAdapter<BootDayGroupModel, BoostListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBootBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemBootBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BootDayGroupModel) {
            binding.textView.text = buildString {
                append(item.date)
                append(" - ")
                append(item.count)
            }
        }
    }

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<BootDayGroupModel>() {
        override fun areItemsTheSame(oldItem: BootDayGroupModel, newItem: BootDayGroupModel) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: BootDayGroupModel, newItem: BootDayGroupModel) =
            oldItem == newItem
    }
}