package com.example.android.mycocktailtesting.drinks

import android.os.Bundle
import android.view.*
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

    private lateinit var viewModel: DrinksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val binding = FragmentDrinksBinding.inflate(inflater, container, false)

        val activity = requireNotNull(this.activity)

        val viewModelFactory = DrinksViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DrinksViewModel::class.java]
        this.viewModel = viewModel

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvDrinks.layoutManager = layoutManager

        val randomDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })
        val popularDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })
        val favoriteDrinksAdapter =
            DrinksAdapter(DrinksAdapter.OnClickListener { viewModel.navigateToSelectedDrink(it) })

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

        setHasOptionsMenu(true)
        return binding.root
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

