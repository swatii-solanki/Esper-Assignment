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
import com.esperassignment.data.api.API
import com.esperassignment.databinding.ActivityMainBinding
import com.esperassignment.data.local.db.FeatureDatabase
import com.esperassignment.data.local.entity.MExclusion
import com.esperassignment.data.local.entity.MFeature
import com.esperassignment.data.local.entity.MOption
import com.esperassignment.data.repository.LocalRepo
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

    // Initialize Variable
    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Context
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: FeatureAdapter
    private lateinit var loader: MyLoader

    private var exclusions: ArrayList<ArrayList<MExclusion>> = ArrayList()
    private val map = HashMap<String, String>()
    private val selectedValue = HashMap<String, MOption>()

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

        clickListener()

        // Checking Whether Network is available or not

        if (Utility.isNetworkAvailable(context)) {
            fetchDataFromNetwork()
        } else {
            fetchDataFromLocal()
        }
    }

    private fun clickListener() {
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
        // Initialize room database object
        val database = FeatureDatabase.getDatabase(this)

        // Initialize Local Repository
        val localRepo = LocalRepo(database.dbDao())

        // Initialize View Model & Pass Local Repository Instance
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

    // Fetching data from API If Internet is connected
    private fun fetchDataFromNetwork() {
        loader.show()

        viewModel.getFeatureList().observe(this, {
            loader.dismiss()
            it.let {
                binding.rv.visibility = View.VISIBLE
                binding.btnSelect.visibility = View.VISIBLE
                adapter.featureList = it as ArrayList<MFeature>
                viewModel.insertFeature(it)
            }
        })

        viewModel.getExclusionList().observe(this, {
            it.forEach {
                exclusions.add(it as java.util.ArrayList<MExclusion>)
                Log.d(TAG, "selected: ${exclusions.toString()}")
                viewModel.insertExclusion(it)
            }
        })
    }

    // Fetching data from room database if Internet is not connected
    private fun fetchDataFromLocal() {
        adapter.featureList.clear()
        exclusions.clear()
        loader.show()
        viewModel.getFeatures().observe(this, { mFeatures ->
            loader.dismiss()
            // checking if first time data is not available into database
            if (mFeatures != null && mFeatures.isNotEmpty()) {
                Log.d(TAG, "fetchDataFromNetwork: $mFeatures")
                binding.rv.visibility = View.VISIBLE
                binding.btnSelect.visibility = View.VISIBLE
                adapter.featureList = mFeatures as ArrayList<MFeature>
            } else {
                binding.tvNoInternet.visibility = View.VISIBLE
            }
        })
        viewModel.getExclusion().observe(this, { mExclusion ->
            Log.d(TAG, "fetchDataFromNetwork: $mExclusion")
            if (mExclusion != null)
                exclusions.add(mExclusion as java.util.ArrayList<MExclusion>)
        })
    }

    override fun selected(featureId: String, option: MOption) {

        // Adding selected values
        selectedValue[featureId] = option

        map.clear()

        repeat(exclusions.size) { index ->
            for ((newIndex, i) in exclusions[index].withIndex()) {

                // checking the selected option id if it is equal then select the opposite array value & store it into map

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