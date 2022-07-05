package com.example.android.mycocktailtesting.drinks

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.chip.Chip

class DrinksFragment : Fragment() {

    private lateinit var viewModel: DrinksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("DrinksFragment", "onCreate")
    }

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
        val latestDrinksAdapter =
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
        viewModel.latestDrink.observe(viewLifecycleOwner, Observer {
            latestDrinksAdapter.submitList(it)
        })
        viewModel.favoriteDrink.observe(viewLifecycleOwner, Observer {
            favoriteDrinksAdapter.submitList(it)
        })

        // Setup Chip Groups for the filters

        viewModel.filterList.observe(viewLifecycleOwner, Observer {

            val chipGroup = binding.drinkFilterList
            val inflater = LayoutInflater.from(chipGroup.context)

            val children = it.map { filterName ->

                val chip = inflater.inflate(R.layout.drink_filter, chipGroup, false) as Chip

                chip.text = filterName
                if(chip.text == viewModel.filter.value!!.value){
                    chip.isChecked = true
                }
                chip.setOnCheckedChangeListener { button, isChecked ->
                    if (isChecked) {
                        viewModel.updateFilter(
                            when (button.text) {
                                CocktailDatabaseFilter.SHOW_TODAYS.value -> CocktailDatabaseFilter.SHOW_TODAYS
                                CocktailDatabaseFilter.SHOW_POPULAR.value -> CocktailDatabaseFilter.SHOW_POPULAR
                                CocktailDatabaseFilter.SHOW_LATEST.value -> CocktailDatabaseFilter.SHOW_LATEST
                                else -> CocktailDatabaseFilter.SHOW_FAVORITE
                            }
                        )
                    }
                }
                chip
            }
            chipGroup.removeAllViews()

            for (chip in children) {
                chipGroup.addView(chip)
            }
        })


        // Replace the adapter for chosen filter
        viewModel.filter.observe(viewLifecycleOwner, Observer { filter ->
            when (filter) {
                CocktailDatabaseFilter.SHOW_TODAYS -> binding.rvDrinks.adapter = randomDrinksAdapter
                CocktailDatabaseFilter.SHOW_POPULAR -> binding.rvDrinks.adapter = popularDrinksAdapter
                CocktailDatabaseFilter.SHOW_LATEST -> binding.rvDrinks.adapter = latestDrinksAdapter
                CocktailDatabaseFilter.SHOW_FAVORITE -> binding.rvDrinks.adapter = favoriteDrinksAdapter
            }
        })

        viewModel.navigateToSelectedDrink.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(DrinksFragmentDirections.actionShowDetail(it))
                viewModel.navigateToSelectedDrinkComplete()
            }
        })

        val toolbar: Toolbar = activity.findViewById(R.id.toolbar)

        return binding.root
    }
}


