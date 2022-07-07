package com.example.android.mycocktailtesting.addlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.mycocktailtesting.databinding.FragmentAddLogBinding
import com.example.android.mycocktailtesting.domain.Log


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

        val viewModelFactory = AddLogViewModelFactory(activity.application, logId)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddLogViewModel::class.java]
        this.viewModel = viewModel

        binding.btnAdd.setOnClickListener {
            if (logId == AddLogViewModel.DEFAULT_LOG_ID) {
                val cocktailName = binding.etCocktailName
                val cocktailPrice = binding.etPrice
                val comment = binding.etComment
                val rating = binding.rbCocktail
                val place = binding.etPlace
                // val photoPath = binding.ivCocktailPhoto

                Log(cocktailName, cocktailPrice, comment, rating, place)
            }
        }

        viewModel.log.observe(viewLifecycleOwner, Observer {
            binding.apply {
                etCocktailName.setText(it.nameLog)
                etPrice.setText(it.priceLog.toString())
            }
        })



        return binding.root
    }
}
