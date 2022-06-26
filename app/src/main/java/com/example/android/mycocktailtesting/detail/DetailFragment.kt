package com.example.android.mycocktailtesting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.mycocktailtesting.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        val activity = requireNotNull(this.activity)
        val drink = DetailFragmentArgs.fromBundle(arguments!!).selectedDrink

        val viewModelFactory = DetailViewModelFactory(drink, activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        this.viewModel = viewModel

        binding.selectedStrDrink.text = viewModel.selectedDrink.value!!.strDrink

        return binding.root
    }
}