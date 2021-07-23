package com.jaidev.videofy.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaidev.videofy.BR
import com.jaidev.videofy.R
import com.jaidev.videofy.databinding.ItemEmptyBinding

class DataBindingVH(view: View) : RecyclerView.ViewHolder(view) {

    lateinit var binding: ViewDataBinding

    companion object {
        fun create(parent: ViewGroup, @LayoutRes layoutId: Int): DataBindingVH {
            val inflater = LayoutInflater.from(parent.context)
            val bindingLocal =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            return DataBindingVH(bindingLocal.root).apply {
                this.binding = bindingLocal
            }
        }
    }

}

class EmptyVH(view: View) : RecyclerView.ViewHolder(view) {

    lateinit var binding: ItemEmptyBinding

    companion object {
        fun create(parent: ViewGroup): EmptyVH {
            val inflater = LayoutInflater.from(parent.context)
            val bindingLocal =
                DataBindingUtil.inflate<ItemEmptyBinding>(
                    inflater,
                    R.layout.item_empty,
                    parent,
                    false
                )
            return EmptyVH(bindingLocal.root).apply {
                this.binding = bindingLocal
            }
        }
    }

}

class LinearLayoutAdapter<E : ListItem>(
    @LayoutRes val layoutId: Int,
    var dataSource: List<ListItem> = arrayListOf(),
    val callback: RecyclerViewCallback? = null,
    private val emptyTextRes: Int = -1
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class ViewType {
        DATA_BINDING, EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == ViewType.DATA_BINDING.ordinal)
            DataBindingVH.create(parent, layoutId)
        else
            EmptyVH.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DataBindingVH -> {
                callback?.let {
                    holder.binding.setVariable(BR.callback, it)
                }
                holder.binding.setVariable(BR.model, dataSource[position])
                holder.binding.executePendingBindings()
            }
            is EmptyVH -> {
                if (emptyTextRes != -1) {
                    holder.binding.textEmptyMessage.setText(emptyTextRes)
                }
            }
            else -> {
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is DataBindingVH -> {
                holder.binding.unbind()
            }
            is EmptyVH -> {
                holder.binding.unbind()
            }
        }
    }


    override fun getItemCount() = dataSource.size.takeIf { it != 0 } ?: 1

    override fun getItemViewType(position: Int) =
        if (position == 0 && dataSource.isEmpty()) ViewType.EMPTY.ordinal
        else ViewType.DATA_BINDING.ordinal
}

//Callback and utility classes
interface RecyclerViewCallback {

    fun onClick(item: ListItem) {

    }
}

interface ListItem {
    fun getType() = 0

}

//Extensions

fun <E : ListItem> RecyclerView.addOrUpdateDataSource(
    dataSource: List<E>,
    @LayoutRes layoutId: Int,
    callback: RecyclerViewCallback?,
    emptyTextRes: Int
) {
    if (adapter == null) {
        layoutManager = LinearLayoutManager(context)
        val adapter = LinearLayoutAdapter<E>(layoutId, dataSource, callback, emptyTextRes)
        setAdapter(adapter)
    } else {
        updateDataSource(dataSource)
    }
}

fun <E : ListItem> RecyclerView.getBindingAdapter(): LinearLayoutAdapter<E>? {
    if (adapter is LinearLayoutAdapter<*>) {
        return adapter as LinearLayoutAdapter<E>?
    }
    return null
}

fun <E : ListItem> RecyclerView.updateDataSource(dataSource: List<E>) {
    getBindingAdapter<E>()?.let {
        it.dataSource = dataSource
        it.notifyDataSetChanged()
    }
}

fun <E : ListItem> RecyclerView.notifyDataSet() {
    getBindingAdapter<E>()?.let {
        it.notifyDataSetChanged()
    }
}