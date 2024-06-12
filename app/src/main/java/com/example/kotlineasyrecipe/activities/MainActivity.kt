package com.example.kotlineasyrecipe.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlineasyrecipe.R
import com.example.kotlineasyrecipe.db.MealDatabase
import com.example.kotlineasyrecipe.viewModel.HomeViewModel
import com.example.kotlineasyrecipe.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val homeMvvm: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        var navController = Navigation.findNavController(this, R.id.frag_host)

        NavigationUI.setupWithNavController(bottomNavigation, navController)


    }
}