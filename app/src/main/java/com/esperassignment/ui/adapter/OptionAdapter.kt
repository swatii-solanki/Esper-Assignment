package com.esperassignment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esperassignment.R
import com.esperassignment.databinding.ItemOptionBinding
import com.esperassignment.model.MOption

class OptionAdapter : RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    private lateinit var binding: ItemOptionBinding
    private lateinit var context: Context
    private var lastSelectedPosition = -1

    var optionList: List<MOption> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: ItemOptionBinding) : RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemOptionBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_option,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = optionList[position]
        holder.binding.textView.text = option.name

        Glide.with(context)
            .load(option.icon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.iv)

        holder.binding.radioButton.isChecked = lastSelectedPosition == position;

        holder.binding.radioButton.setOnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = optionList.size
}