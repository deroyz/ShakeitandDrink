package com.example.android.mycocktailtesting.ui.logs

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mycocktailtesting.R
import com.example.android.mycocktailtesting.databinding.FragmentLogsBinding


class LogsFragment : Fragment(), MenuProvider {

    private lateinit var viewModel: LogsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

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


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Add menu items here
        menuInflater.inflate(R.menu.menu_item, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        return true
    }
}