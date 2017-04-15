package com.calintat.sensors.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.calintat.sensors.R
import org.jetbrains.anko.find

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val time = itemView.find<TextView>(R.id.logger_list_item_time)

    val data = itemView.find<TextView>(R.id.logger_list_item_data)
}