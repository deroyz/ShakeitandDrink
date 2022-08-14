package com.example.android.mycocktailtesting.drink.drinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.ListDrinkBinding
import com.example.android.mycocktailtesting.data.domain.Drink

class DrinksAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Drink, DrinksAdapter.DrinksViewHolder>(DrinkDiffCallback()) {

    class DrinksViewHolder private constructor(val binding: ListDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Drink) {
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
            fun from(parent: ViewGroup): DrinksViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListDrinkBinding.inflate(layoutInflater, parent, false)
                return DrinksViewHolder(binding)
            }
        }
    }

    class DrinkDiffCallback : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        return DrinksViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (drink: Drink) -> Unit) {
        fun onClick(drink: Drink) = clickListener(drink)
    }
}


