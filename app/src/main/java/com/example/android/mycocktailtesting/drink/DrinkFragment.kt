package com.example.android.mycocktailtesting.drink

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.databinding.FragmentDrinksBinding

class DrinkFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val binding = FragmentDrinksBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = requireNotNull(this.activity)

        val viewModelFactory = DrinkViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DrinkViewModel::class.java]
        this.viewModel = viewModel

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvDrinks.layoutManager = layoutManager

        val randomDrinkAdapter = DrinkAdapter()
        val popularDrinkAdapter = DrinkAdapter()
        val favoriteDrinkAdapter = DrinkAdapter()

        viewModel.randomDrinkList.observe(viewLifecycleOwner, Observer {
            randomDrinkAdapter.submitList(it)
        })
        viewModel.popularDrinkList.observe(viewLifecycleOwner, Observer {
            popularDrinkAdapter.submitList(it)
        })
        viewModel.favoriteDrink.observe(viewLifecycleOwner, Observer {
            favoriteDrinkAdapter.submitList(it)
        })
        viewModel.filter.observe(viewLifecycleOwner, Observer { filter ->
            when (filter) {
                CocktailDatabaseFilter.SHOW_TODAYS -> binding.rvDrinks.adapter = randomDrinkAdapter
                CocktailDatabaseFilter.SHOW_POPULAR -> binding.rvDrinks.adapter =
                    popularDrinkAdapter
                CocktailDatabaseFilter.SHOW_FAVORITE -> binding.rvDrinks.adapter =
                    favoriteDrinkAdapter
            }
        })

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drink_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.todays_filter -> CocktailDatabaseFilter.SHOW_TODAYS
                R.id.popular_filter -> CocktailDatabaseFilter.SHOW_POPULAR
                else -> CocktailDatabaseFilter.SHOW_FAVORITE
            }
        )
        return true
    }
}