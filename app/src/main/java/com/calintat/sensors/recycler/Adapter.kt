package com.calintat.sensors.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.calintat.sensors.api.Snapshot
import com.calintat.sensors.ui.ListItem
import org.jetbrains.anko.AnkoContext

class Adapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    val items = mutableListOf<Snapshot>()

    fun add(item: Snapshot) {

        items.add(0, item)

        notifyItemInserted(0)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ListItem.createView(AnkoContext.create(context, parent)))
    }
}