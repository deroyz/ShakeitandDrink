package com.example.android.mycocktailtesting.log.logs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.ListLogBinding
import com.example.android.mycocktailtesting.model.domain.DomainLog

class LogsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DomainLog, LogsAdapter.LogsViewHolder>(DomainLogDiffCallback()) {

    class LogsViewHolder private constructor(val binding: ListLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DomainLog) {
            binding.apply{
                tvLogCocktailName.text = item.nameLog
                tvCocktailPrice.text = item.priceLog.toString()
                tvRating.text = item.ratingLog.toString()
                tvComment.text = item.commentLog

                ivLogCocktail
                Glide.with(binding.ivLogCocktail.context)
                    .load(R.drawable.ic_broken_image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.ivLogCocktail)
            }
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

