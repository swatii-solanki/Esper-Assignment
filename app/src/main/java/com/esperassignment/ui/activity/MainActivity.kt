package com.esperassignment.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.esperassignment.R
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.ui.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Context
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        context = this
        init()
    }

    private fun init() {
        initializeViewModel()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

}