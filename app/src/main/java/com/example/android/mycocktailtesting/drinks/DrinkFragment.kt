package com.example.android.mycocktailtesting.drinks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycocktailtesting.databinding.FragmentDrinksBinding

class DrinkFragment : Fragment() {

    private lateinit var viewModel: DrinkViewModel
    private lateinit var viewModelFactory: DrinkViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        Log.e("DrinkFragment", "onCreateView1")

        val activity = requireNotNull(this.activity)

        viewModelFactory = DrinkViewModelFactory(activity.application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DrinkViewModel::class.java]
        Log.e("DrinkFragment", "$viewModel")

        val binding = FragmentDrinksBinding.inflate(inflater, container, false)
        val view = binding.root

        val drinkAdapter = DrinkAdapter()
        binding.rvDrinks.adapter = drinkAdapter

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvDrinks.layoutManager = layoutManager

        viewModel.drinkList.observe(viewLifecycleOwner, Observer {
            drinkAdapter.submitList(it)
        })

        return view
    }


}