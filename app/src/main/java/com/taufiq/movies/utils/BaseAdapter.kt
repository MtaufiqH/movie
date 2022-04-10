package com.taufiq.movies.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private val layoutId: Int) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    protected var data = ArrayList<T>()
    protected var limit: Int? = null

    var itemClickListener : ((T) -> Unit)? = null

    class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return limit ?: data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
    )

    fun setData(listItem: List<T>?) {
        if (listItem.isNullOrEmpty()) return
        data.clear()
        data.addAll(listItem)
        notifyDataSetChanged()
    }

}