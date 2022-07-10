package com.example.android.mycocktailtesting.logs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycocktailtesting.databinding.ListLogBinding
import com.example.android.mycocktailtesting.domain.DomainLog

class LogsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DomainLog, LogsAdapter.LogsViewHolder>(DomainLogDiffCallback()) {

    class LogsViewHolder private constructor(val binding: ListLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DomainLog) {
            binding.tvLogCocktailName.text = item.nameLog
            binding.tvCocktailPrice.text = item.priceLog.toString()
            binding.tvRating.text = item.ratingLog.toString()
        }

        companion object {
            fun from(parent: ViewGroup): LogsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListLogBinding.inflate(layoutInflater, parent, false)
                return LogsViewHolder(binding)
            }
        }
    }

    class DomainLogDiffCallback : DiffUtil.ItemCallback<DomainLog>() {

        override fun areItemsTheSame(oldItem: DomainLog, newItem: DomainLog): Boolean {
            return oldItem.idLog == newItem.idLog
        }

        override fun areContentsTheSame(oldItem: DomainLog, newItem: DomainLog): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (log: DomainLog) -> Unit) {
        fun onClick(log: DomainLog) = clickListener(log)
    }
}

