package com.example.android.mycocktailtesting.addlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.mycocktailtesting.databinding.FragmentAddLogBinding


class AddLogFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddLogBinding.inflate(inflater, container, false)

        return binding.root
    }
}