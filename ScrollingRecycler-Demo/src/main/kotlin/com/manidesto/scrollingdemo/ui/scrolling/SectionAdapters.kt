package com.manidesto.scrollingdemo.ui.scrolling

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.manidesto.scrollingdemo.R
import com.manidesto.scrollingdemo.ui.commons.FakeAdapters
import com.manidesto.scrollingdemo.ui.commons.inflate

/**
 * Author: carozhu
 * Date  : On 2018/11/28
 * Desc  :
 */
class SectionAdapters : RecyclerView.Adapter<SectionAdapters.ViewHolder>() {
    val DEFAULT_COUNT = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionAdapters.ViewHolder {
        return SectionAdapters.ViewHolder(parent!!.inflate(R.layout.item_section))
    }

    override fun getItemCount() = DEFAULT_COUNT

    override fun onBindViewHolder(holder: SectionAdapters.ViewHolder, position: Int) {
        holder?.bind()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var rvHorizontal : RecyclerView
        lateinit var layoutManager : LinearLayoutManager
        init {
            rvHorizontal = itemView.findViewById(R.id.rv_horizontal) as RecyclerView
            layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            rvHorizontal.layoutManager = layoutManager
            rvHorizontal.adapter = FakeAdapters(R.layout.item_card_hor)
        }

        fun bind() {
            layoutManager.scrollToPosition(0)
        }
    }
}