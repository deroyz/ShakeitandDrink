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

    private val application = requireNotNull(this.activity).application
    private val viewModelFactory = DrinkViewModelFactory(application)
    private val viewModel: DrinkViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DrinkViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.e("DrinkFragment", "onCreateView1")

        val binding = FragmentDrinksBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.e("DrinkFragment", "onCreateView2")


        val drinkAdapter = DrinkAdapter()
        binding.rvDrinks.adapter = drinkAdapter
        Log.e("DrinkFragment", "onCreateView3")

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvDrinks.layoutManager = layoutManager
        Log.e("DrinkFragment", "onCreateView4")

        viewModel.drinkList.observe(viewLifecycleOwner, Observer {
            drinkAdapter.submitList(it)
        })

        return view
    }


}