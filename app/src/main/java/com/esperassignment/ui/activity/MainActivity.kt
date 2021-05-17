package com.esperassignment.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esperassignment.R
import com.esperassignment.api.API
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.model.MExclusion
import com.esperassignment.model.MOption
import com.esperassignment.ui.adapter.FeatureAdapter
import com.esperassignment.ui.adapter.OptionAdapter
import com.esperassignment.ui.viewmodel.MainActivityViewModel
import com.esperassignment.utils.MyLoader
import com.esperassignment.utils.Utility

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
    private val selectedValue = HashMap<String, MOption>()
    private lateinit var loader: MyLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        context = this
        init()
    }

    private fun init() {
        loader = MyLoader(context)
        initializeViewModel()
        setRecyclerView()
        if (Utility.isNetworkAvailable(context)) {
            fetchDataFromNetwork()
        } else {
            binding.tvNoInternet.visibility = View.VISIBLE
        }
        binding.btnSelect.setOnClickListener {
            if (selectedValue.size > 0) {
                val intent = Intent(this, SelectedActivity::class.java)
                intent.putExtra(API.DATA, selectedValue)
                startActivity(intent)
            } else
                Utility.showSnackBar(this, binding.root, getString(R.string.select_validation))
        }
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

    private fun fetchDataFromNetwork() {
        loader.show()
        viewModel.dbList().observe(this, {
            loader.dismiss()
            it.let {
                binding.rv.visibility = View.VISIBLE
                binding.btnSelect.visibility = View.VISIBLE
                adapter.featureList = it.features
                exclusions = it.exclusions
            }
        })
    }

    override fun selected(featureId: String, option: MOption) {
        selectedValue[featureId] = option
        map.clear()
        repeat(exclusions.size) { index ->
            for ((newIndex, i) in exclusions[index].withIndex()) {
                if (option.id == i.options_id) {
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