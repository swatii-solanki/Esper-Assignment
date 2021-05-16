package com.esperassignment.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esperassignment.R
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.model.MExclusion
import com.esperassignment.ui.adapter.FeatureAdapter
import com.esperassignment.ui.adapter.OptionAdapter
import com.esperassignment.ui.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), OptionAdapter.OnSelection {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Context
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: FeatureAdapter
    private var exclusions: List<List<MExclusion>> = ArrayList()
    private val map = HashMap<String, String>()

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
        adapter = FeatureAdapter(this)
        binding.rv.adapter = adapter
    }

    private fun featureList() {
        viewModel.dbList().observe(this, {
            it.let {
                adapter.featureList = it.features
                exclusions = it.exclusions
            }
        })
    }

    override fun selected(optionId: String) {
        map.clear()
        repeat(exclusions.size) { index ->
            for ((newIndex, i) in exclusions[index].withIndex()) {
                if (optionId == i.options_id) {
                    if (newIndex == 0) {
                        map[exclusions[index][newIndex + 1].feature_id] =
                            exclusions[index][newIndex + 1].options_id
                        adapter.onItemChanged(map, exclusions[index][newIndex + 1].feature_id)
                    } else {
                        map[exclusions[index][newIndex - 1].feature_id] =
                            exclusions[index][newIndex - 1].options_id
                        adapter.onItemChanged(map, exclusions[index][newIndex - 1].feature_id)
                    }
                }
            }
        }
    }
}