package com.example.android.mycocktailtesting.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate Layout
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        // Setup ViewBinding
        viewModel.selectedDrink.observe(viewLifecycleOwner, Observer {
            binding.strDrink.text = it.strDrink
            binding.strCategory.text = "Category: " + it.strCategory
            binding.strGlass.text = "Glass: " + it.strGlass
            binding.strInstructions.text = "Instructions: " + it.strInstructions

            binding.strDrinkThumb
            Glide.with(binding.strDrinkThumb.context)
                .load(it.strDrinkThumb)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(binding.strDrinkThumb)
        })

        // Setup OnClickListener
        binding.imageButton2.setOnClickListener(View.OnClickListener {
            viewModel.favoriteStatusUpdate()
        })

        // Setup Observers
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }
}