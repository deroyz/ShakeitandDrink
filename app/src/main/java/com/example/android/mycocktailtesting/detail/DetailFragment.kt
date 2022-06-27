package com.example.android.mycocktailtesting.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

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

        binding.imageButton2.setOnClickListener(View.OnClickListener {
            viewModel.updateFavoriteStatus()
        })

        viewModel.selectedDrink.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                viewModel.viewBinding(it, binding)
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    viewModel.favoriteBtnActive()
                } else {
                    viewModel.favoriteBtnInactive()
                }
            }
        })

        return binding.root
    }
}