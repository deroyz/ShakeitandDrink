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
        Log.e("AddLogFragment", "1")
        val activity = requireNotNull(this.activity)
        Log.e("AddLogFragment", "2")

        val logId = AddLogFragmentArgs.fromBundle(arguments!!).selectedLogId
        Log.e("AddLogFragment", "logId = $logId")

        val viewModelFactory = AddLogViewModelFactory(activity.application, logId)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddLogViewModel::class.java]
        this.viewModel = viewModel
        Log.e("AddLogFragment", "4")

        binding.btnAdd.setOnClickListener(View.OnClickListener {

            val nameLog = binding.etCocktailName.text.toString()
            var priceLog = binding.etPrice.toString().toDoubleOrNull()
            if(priceLog == null){
                priceLog = 0.0
            }
            val ratingLog = binding.rbCocktail.rating.toDouble()
            val placeLog = "hi"
            val commentLog = binding.etComment.text.toString()

            Log.e("AddLogFragment", "5")

            val logEntry =
                DomainLog(logId, nameLog, priceLog, ratingLog, placeLog, commentLog)

            viewModel.onAddButtonClicked(logEntry)

        })

        viewModel.domainLog.observe(viewLifecycleOwner, Observer
        {
            binding.apply {
                Log.e("AddLogFragment", "7")
                etCocktailName.setText(it.nameLog)
                etPrice.setText(it.priceLog.toString())
            }
        })

        return binding.root
    }
}
