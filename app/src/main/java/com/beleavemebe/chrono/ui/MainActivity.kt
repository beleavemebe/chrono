package com.beleavemebe.chrono.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beleavemebe.chrono.R
import com.beleavemebe.chrono.databinding.ActivityMainBinding
import com.beleavemebe.chrono.db.AppDatabase

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        AppDatabase.initialize(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = supportFragmentManager.
                findFragmentById(R.id.fragment_container)!!.findNavController()
        setupToolbar(navController)
        setupBottomNavigation(navController)
    }

    private fun setupToolbar(navController: NavController) {
        val appBarConfiguration = configureAppBar()
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        setSupportActionBar(binding.toolbar)
    }

    private fun configureAppBar(): AppBarConfiguration {
        val topLevelDestinations = setOf(R.id.fragment_chrono, R.id.fragment_diary)
        return AppBarConfiguration(topLevelDestinations)
    }

    private fun setupBottomNavigation(navController: NavController) {
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}
