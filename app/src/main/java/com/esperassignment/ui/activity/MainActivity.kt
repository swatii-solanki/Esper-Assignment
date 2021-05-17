package com.esperassignment.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esperassignment.R
import com.esperassignment.api.API
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.local.db.FeatureDatabase
import com.esperassignment.repository.LocalRepo
import com.esperassignment.local.entity.MExclusion
import com.esperassignment.local.entity.MOption
import com.esperassignment.ui.adapter.FeatureAdapter
import com.esperassignment.ui.adapter.OptionAdapter
import com.esperassignment.ui.viewmodel.MainActivityViewModel
import com.esperassignment.ui.viewmodel.MainActivityViewModelFactory
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
    private var exclusions: List<MExclusion> = ArrayList()
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
            fetchDataFromLocal()
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
        val database = FeatureDatabase.getDatabase(this)
        val localRepo = LocalRepo(database.dbDao())
        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelFactory(localRepo)
        ).get(MainActivityViewModel::class.java)
    }

    private fun setRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.setHasFixedSize(true)
        adapter = FeatureAdapter(this)
        binding.rv.adapter = adapter
    }

    private fun fetchDataFromNetwork() {
        loader.show()
        viewModel.getFeatureList().observe(this, {
            loader.dismiss()
            it.let {
                binding.rv.visibility = View.VISIBLE
                binding.btnSelect.visibility = View.VISIBLE
                adapter.featureList = it
                viewModel.insertFeature(it)
            }
        })
        viewModel.getExclusionList().observe(this, {
            it.forEach {
                exclusions = it
                viewModel.insertExclusion(it)
            }
        })
    }

    private fun fetchDataFromLocal() {
        loader.show()
        viewModel.getFeatures().observe(this, { mFeatures ->
            loader.dismiss()
            if (mFeatures != null) {
                Log.d(TAG, "fetchDataFromNetwork: $mFeatures")
                binding.rv.visibility = View.VISIBLE
                binding.btnSelect.visibility = View.VISIBLE
                adapter.featureList = mFeatures.features
            } else {
                binding.tvNoInternet.visibility = View.VISIBLE
            }
        })
        viewModel.getExclusion().observe(this, { mExclusion ->
            Log.d(TAG, "fetchDataFromNetwork: $mExclusion")
            if (mExclusion != null)
                exclusions = mExclusion
        })
    }

    override fun selected(featureId: String, option: MOption) {
        selectedValue[featureId] = option
        map.clear()
        for ((newIndex, i) in exclusions.withIndex()) {
            if (option.id == i.options_id) {
                if (newIndex == 0) {
                    map[exclusions[newIndex + 1].feature_id] =
                        exclusions[newIndex + 1].options_id
                    adapter.onItemChanged(map, exclusions[newIndex + 1].feature_id)
                } else {
                    map[exclusions[newIndex - 1].feature_id] =
                        exclusions[newIndex - 1].options_id
                    adapter.onItemChanged(map, exclusions[newIndex - 1].feature_id)
                }
            }
        }
    }
}