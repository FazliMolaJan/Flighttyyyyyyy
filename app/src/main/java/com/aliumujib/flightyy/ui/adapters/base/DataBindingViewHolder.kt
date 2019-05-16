package com.aliumujib.flightyy.ui.adapters.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.flightyy.BR

class DataBindingViewHolder<T>(private val binding: ViewDataBinding, var itemClickListener: BindableItemClickListener<T>) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.clickListener, itemClickListener)
        binding.executePendingBindings()
    }
}