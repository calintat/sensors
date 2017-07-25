package com.calintat.sensors.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.calintat.sensors.api.Logger
import com.calintat.sensors.ui.ListItem
import org.jetbrains.anko.AnkoContext

class Adapter : RecyclerView.Adapter<ViewHolder>() {

    val items = mutableListOf<Logger.Snapshot>()

    fun add(item: Logger.Snapshot) {

        items.add(0, item)

        notifyItemInserted(0)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ListItem.createView(AnkoContext.create(parent.context, parent)))
    }
}