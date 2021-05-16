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

class FeatureAdapter(private var onSelection: OptionAdapter.OnSelection) :
    RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {

    private lateinit var binding: ItemFeatureBinding
    private lateinit var context: Context
    private lateinit var optionAdapter: OptionAdapter
    var map: HashMap<String, String> = HashMap()
    var lastIndex: Int = -1

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
        optionAdapter = OptionAdapter(onSelection)
        optionAdapter.map = map
        optionAdapter.optionList = feature.options
        holder.binding.rv.layoutManager = LinearLayoutManager(context)
        holder.binding.rv.setHasFixedSize(true)
        holder.binding.rv.adapter = optionAdapter
    }

    override fun getItemCount() = featureList.size

    fun onItemChanged(map: HashMap<String, String>, featureId: String) {
        featureList.forEachIndexed { index, mFeature ->
            if (mFeature.feature_id == featureId) {
                this.map = map
                if (lastIndex == -1)
                    lastIndex = index
                notifyItemChanged(index)
                notifyItemChanged(lastIndex)
                lastIndex = index
            }
        }
    }
}