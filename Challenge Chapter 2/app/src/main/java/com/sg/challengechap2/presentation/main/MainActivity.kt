package com.sg.challengechap2.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sg.challengechap2.R
import com.sg.challengechap2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}