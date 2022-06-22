package com.example.android.mycocktailtesting.drinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.ListDrinkBinding
import com.example.android.mycocktailtesting.network.NetworkDrink

class DrinkAdapter : ListAdapter<NetworkDrink, DrinkAdapter.DrinkViewHolder>(DrinkDiffCallback()) {

    class DrinkViewHolder private constructor(val binding: ListDrinkBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NetworkDrink) {
            binding.ivDrinkCocktail
            Glide.with(binding.ivDrinkCocktail.context)
                .load(item.strDrinkThumb)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.ivDrinkCocktail)
            binding.tvDrinkCocktailName.text = item.strDrink
        }

        companion object {
            fun from(parent: ViewGroup): DrinkViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListDrinkBinding.inflate(layoutInflater, parent, false)
                return DrinkViewHolder(binding)
            }
        }
    }

    class DrinkDiffCallback: DiffUtil.ItemCallback<NetworkDrink>() {
        override fun areItemsTheSame(oldItem: NetworkDrink, newItem: NetworkDrink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: NetworkDrink, newItem: NetworkDrink): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}


