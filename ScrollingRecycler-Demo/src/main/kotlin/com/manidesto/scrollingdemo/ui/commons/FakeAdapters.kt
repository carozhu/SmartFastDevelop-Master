package com.manidesto.scrollingdemo.ui.commons

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup

/**
 * Author: carozhu
 * Date  : On 2018/11/28
 * Desc  :
 */
class FakeAdapters(@LayoutRes var layoutRes : Int) : RecyclerView.Adapter<FakeAdapters.ViewHolder>(){

    val DEFAULT_COUNT = 10
    var count = DEFAULT_COUNT
        set(value) {
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FakeAdapters.ViewHolder(inflate(layoutRes, parent!!))
    }

    override fun getItemCount() = count

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /** nothing to do **/
    }

    fun inflate(@LayoutRes layoutRes: Int, parent : ViewGroup) : View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(layoutRes, parent, false);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}