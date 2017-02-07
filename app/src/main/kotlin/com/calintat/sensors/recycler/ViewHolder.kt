package com.calintat.sensors.recycler

import android.view.View
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.calintat.sensors.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val time = itemView.findViewById(R.id.logger_list_item_time) as TextView

    val measurement = itemView.findViewById(R.id.logger_list_item_measurement) as TextView
}