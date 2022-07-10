package com.example.android.mycocktailtesting.logs

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
import com.example.android.mycocktailtesting.databinding.FragmentLogsBinding


class LogsFragment : Fragment() {

    private lateinit var viewModel: LogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate Layout
        val binding = FragmentLogsBinding.inflate(inflater, container, false)

        // Get required values
        val activity = requireNotNull(this.activity)

        // Setup ViewModel
        val viewModelFactory = LogsViewModelFactory(activity.application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LogsViewModel::class.java]
        this.viewModel = viewModel

        // Setup RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.rvLogs.layoutManager = layoutManager

        // Setup adapter for log dataset
        val logsAdapter =
            LogsAdapter(LogsAdapter.OnClickListener { viewModel.navigateToSelectedLog(it) })
        binding.rvLogs.adapter = logsAdapter

        // Setup Observer
        viewModel.logList.observe(viewLifecycleOwner, Observer {
            logsAdapter.submitList(it)
        })

//        viewModel.navigateToSelectedLog.observe(viewLifecycleOwner, Observer {
//            if (null != it) {
//                this.findNavController().navigate(LogsFragmentDirections.actionShowLogDetail(it.idLog))
//                viewModel.navigateToSelectedLogComplete()
//            }
//        })

        return binding.root
    }
}