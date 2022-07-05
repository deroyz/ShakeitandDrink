package com.example.android.mycocktailtesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.mycocktailtesting.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
private lateinit var navController: NavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)

        binding?.apply {
            setContentView(root)
            setSupportActionBar(toolbar)

            navController = (supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                .navController
            setupWithNavController(bottomNavigationView, navController)
        }

//        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
//            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
//            when(destination.id) {
//                R.id.drinksFragment -> {
//                    toolBar.setDisplayShowTitleEnabled(false)
//                    binding.heroImage.visibility = View.GONE
//                }
//                else -> {
//                    toolBar.setDisplayShowTitleEnabled(false)
//                    binding.heroImage.visibility = View.VISIBLE
//                }
//            }
//        }
    }
}
