package com.example.android.mycocktailtesting.addlog

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.FragmentAddLogBinding
import com.example.android.mycocktailtesting.domain.DomainLog


class AddLogFragment : Fragment() {

    private lateinit var viewModel: AddLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate Layout
        val binding = FragmentAddLogBinding.inflate(inflater, container, false)
        val activity = requireNotNull(this.activity)

        val logId = AddLogFragmentArgs.fromBundle(arguments!!).selectedLogId
        Log.e("AddLogFragment", "logId = $logId")

        val viewModelFactory = AddLogViewModelFactory(activity.application, logId)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddLogViewModel::class.java]
        this.viewModel = viewModel

        binding.ivCocktailPhoto.setOnClickListener(View.OnClickListener {
            viewModel.onImageButtonClicked()
        })

        binding.btnAdd.setOnClickListener(View.OnClickListener {
            val nameLog = binding.etCocktailName.text.toString()
            var priceLog = binding.etPrice.toString().toDoubleOrNull()
            if (priceLog == null) {
                priceLog = 0.0
            }
            val ratingLog = binding.rbCocktail.rating.toDouble()
            val placeLog = "hi"
            val commentLog = binding.etComment.text.toString()
            val logEntry =
                DomainLog(logId, nameLog, priceLog, ratingLog, placeLog, commentLog)
            viewModel.onAddButtonClicked(logEntry)

        })

        viewModel.domainLog.observe(viewLifecycleOwner, Observer
        {
            binding.apply {
                etCocktailName.setText(it.nameLog)
                etPrice.setText(it.priceLog.toString())
                ivCocktailPhoto
                Glide.with(binding.ivCocktailPhoto.context)
                    .load(R.drawable.ic_broken_image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.ivCocktailPhoto)
            }
        })

        return binding.root
    }
}
