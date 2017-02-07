package com.calintat.sensors.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.calintat.sensors.R

class Adapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    val items = mutableListOf<Pair<Float, FloatArray>>()

    fun add(time: Float, measurement: FloatArray) {

        items.add(0, Pair(time, measurement))

        notifyItemInserted(0)
    }

    override fun getItemCount(): Int {

        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val (time, measurement) = items[position]

        holder.time.text = time.toString()

        holder.measurement.text = measurement.joinToString(separator = "\n")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(context)

        val layoutResource = R.layout.fragment_logger_list_item

        return ViewHolder(layoutInflater.inflate(layoutResource, parent, false))
    }
}