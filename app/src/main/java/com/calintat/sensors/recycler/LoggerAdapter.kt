package com.calintat.sensors.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.calintat.sensors.api.Logger
import com.calintat.sensors.ui.ListItem
import org.jetbrains.anko.AnkoContext

class LoggerAdapter : RecyclerView.Adapter<LoggerViewHolder>() {

    val items = mutableListOf<Logger.Snapshot>()

    private var recyclerView: RecyclerView? = null

    fun add(item: Logger.Snapshot) {

        items.add(item); notifyItemInserted(itemCount - 1)

        recyclerView?.layoutManager?.scrollToPosition(itemCount - 1)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LoggerViewHolder, position: Int) {

        holder.bindItem(items[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {

        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggerViewHolder {

        return LoggerViewHolder(ListItem.createView(AnkoContext.create(parent.context, parent)))
    }
}