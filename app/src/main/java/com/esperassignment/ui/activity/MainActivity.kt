package com.esperassignment.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esperassignment.R
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.ui.adapter.FeatureAdapter
import com.esperassignment.ui.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Context
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: FeatureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        context = this
        init()
    }

    private fun init() {
        initializeViewModel()
        setRecyclerView()
        featureList()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun setRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.setHasFixedSize(true)
        adapter = FeatureAdapter()
        binding.rv.adapter = adapter
    }

    private fun featureList() {
        viewModel.dbList().observe(this, {
            it.let {
                Log.d(TAG, "init: $it")
                adapter.featureList = it.features
            }
        })
    }
}