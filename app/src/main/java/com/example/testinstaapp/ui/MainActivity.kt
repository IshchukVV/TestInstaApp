package com.example.testinstaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testinstaapp.R
import com.example.testinstaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}