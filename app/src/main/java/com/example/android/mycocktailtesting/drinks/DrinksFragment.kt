package com.example.android.mycocktailtesting.drinks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.database.CocktailDatabaseFilter
import com.example.android.mycocktailtesting.databinding.FragmentDrinksBinding

class DrinksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var viewModel: DrinksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate Layout
        val binding = FragmentDrinksBinding.inflate(inflater, container, false)

        // Get required values
        val activity = requireNotNull(this.activity)

        // Setup ViewModel
        val viewModelFactory = DrinksViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DrinksViewModel::class.java]
        this.viewModel = viewModel

        // Setup RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvDrinks.layoutManager = layoutManager

        // Setup adapters for each drink dataset
        val randomDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })
        val popularDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })
        val favoriteDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })

        // Setup Observers
        viewModel.randomDrinkList.observe(viewLifecycleOwner, Observer {
            randomDrinksAdapter.submitList(it)
        })
        viewModel.popularDrinkList.observe(viewLifecycleOwner, Observer {
            popularDrinksAdapter.submitList(it)
        })
        viewModel.favoriteDrink.observe(viewLifecycleOwner, Observer {
            favoriteDrinksAdapter.submitList(it)
        })
        viewModel.filter.observe(viewLifecycleOwner, Observer { filter ->
            when (filter) {
                CocktailDatabaseFilter.SHOW_TODAYS -> binding.rvDrinks.adapter = randomDrinksAdapter
                CocktailDatabaseFilter.SHOW_POPULAR -> binding.rvDrinks.adapter =
                    popularDrinksAdapter
                CocktailDatabaseFilter.SHOW_FAVORITE -> binding.rvDrinks.adapter =
                    favoriteDrinksAdapter
            }
        })
        viewModel.navigateToSelectedDrink.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(DrinksFragmentDirections.actionShowDetail(it))
                viewModel.navigateToSelectedDrinkComplete()
            }
        })

        val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.drink_menu)
        toolbar.setOnMenuItemClickListener {
            viewModel.updateFilter(
                when (it.itemId) {
                    R.id.todays_filter -> CocktailDatabaseFilter.SHOW_TODAYS
                    R.id.popular_filter -> CocktailDatabaseFilter.SHOW_POPULAR
                    else -> CocktailDatabaseFilter.SHOW_FAVORITE
                }
            )
             true
        }
        return binding.root
    }
}


