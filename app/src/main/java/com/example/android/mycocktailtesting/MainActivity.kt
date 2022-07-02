package com.example.android.mycocktailtesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.mycocktailtesting.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupNavigation()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)

    private fun setupNavigation() {
// first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(binding.toolbar)

        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(navController, binding.drawerLayout)


        // finally setup the left drawer (called a NavigationView)
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when (destination.id) {
                R.id.drinksFragment -> {
                    toolBar.setDisplayShowTitleEnabled(false)
                    binding.heroImage.visibility = View.VISIBLE
                }

                else -> {
                    toolBar.setDisplayShowTitleEnabled(true)
                    binding.heroImage.visibility = View.GONE
                }
            }
        }
    }
}