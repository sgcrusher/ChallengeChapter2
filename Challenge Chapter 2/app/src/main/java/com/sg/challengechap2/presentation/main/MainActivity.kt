package com.sg.challengechap2.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sg.challengechap2.R
import com.sg.challengechap2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }
    private fun setupBottomNav() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        binding.navigationView.setupWithNavController(navController)
    }
}
