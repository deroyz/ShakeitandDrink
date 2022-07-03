package com.example.android.mycocktailtesting.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.mycocktailtesting.databinding.FragmentLogBinding

class LogFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLogBinding.inflate(inflater, container, false)

        return binding.root
    }
}