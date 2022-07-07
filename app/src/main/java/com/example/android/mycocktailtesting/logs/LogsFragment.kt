package com.example.android.mycocktailtesting.logs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.mycocktailtesting.databinding.FragmentLogsBinding
import com.example.android.mycocktailtesting.drinks.DrinksViewModel

class LogsFragment: Fragment() {

    private lateinit var viewModel: LogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLogsBinding.inflate(inflater, container, false)




        return binding.root
    }
}