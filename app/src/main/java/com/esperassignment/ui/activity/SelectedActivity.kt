package com.esperassignment.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esperassignment.R
import com.esperassignment.api.API
import com.esperassignment.databinding.ActivitySelectedBinding
import com.esperassignment.model.MOption
import com.esperassignment.utils.Utility

class SelectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedBinding
    private var selectedValue = HashMap<String, MOption>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selected)
        init()
    }

    private fun init() {
        if (intent.hasExtra(API.DATA)) {
            selectedValue = (intent.extras?.getSerializable(API.DATA) as HashMap<String, MOption>)
            selectedValue.forEach { entry ->
                Log.d(TAG, "init: ${entry.toString()}")
                when (entry.key) {
                    "1" -> {
                        binding.iv.visibility = View.VISIBLE
                        binding.textView2.visibility = View.VISIBLE
                        Glide.with(this)
                            .load(entry.value.icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.iv)
                        binding.textView2.text = entry.value.name

                    }
                    "2" -> {
                        binding.textView3.visibility = View.VISIBLE
                        binding.textView4.text = entry.value.name
                    }
                    "3" -> {
                        binding.textView5.visibility = View.VISIBLE
                        binding.textView6.text = entry.value.name
                    }
                }
            }
        } else {
            Utility.showSnackBar(this, binding.root, getString(R.string.try_again))
            onBackPressed()
        }
    }

    companion object {
        private const val TAG = "SelectedActivity"
    }
}