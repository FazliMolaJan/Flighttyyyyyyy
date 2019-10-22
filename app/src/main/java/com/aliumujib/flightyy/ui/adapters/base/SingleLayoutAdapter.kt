package com.aliumujib.flightyy.ui.adapters.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.flightyy.BR
import com.aliumujib.flightyy.ui.utils.DiffUtilCallback
import com.aliumujib.flightyy.ui.utils.ext.inflater

import kotlin.properties.Delegates.observable

class SingleLayoutAdapter<T>(@LayoutRes private val resId: Int, val itemClickListener: BindableItemClickListener<T>? = null) :
    RecyclerView.Adapter<SingleLayoutAdapter.ViewHolder<T>>(), BindableAdapter<T> {

    override fun setData(data: List<T>) {
        items = data
    }

    var items by observable<List<T>>(listOf()) { _, oldValue, newValue ->
        DiffUtil
            .calculateDiff(DiffUtilCallback(oldValue, newValue))
            .dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder<T>(parent.inflate(), itemClickListener)

    private fun ViewGroup.inflate() = DataBindingUtil.inflate<ViewDataBinding>(inflater, resId, this, false)

    fun clearItems() {
        setData(emptyList())
    }

    class ViewHolder<in T>(
        private val binding: ViewDataBinding,
        private var itemClickListener: BindableItemClickListener<T>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) = with(binding) {
            setVariable(BR.item, item)
            setVariable(BR.clickListener, itemClickListener)
            executePendingBindings()
        }
    }
}
