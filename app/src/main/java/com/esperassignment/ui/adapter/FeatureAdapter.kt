package com.esperassignment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esperassignment.R
import com.esperassignment.databinding.ItemFeatureBinding
import com.esperassignment.model.MFeature

class FeatureAdapter : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {

    private lateinit var binding: ItemFeatureBinding
    private lateinit var context: Context

    var featureList: List<MFeature> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: ItemFeatureBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemFeatureBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_feature,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature = featureList[position]
        holder.binding.textView.text = feature.name

        val optionAdapter = OptionAdapter()
        optionAdapter.optionList = feature.options
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = optionAdapter
    }

    override fun getItemCount() = featureList.size
}