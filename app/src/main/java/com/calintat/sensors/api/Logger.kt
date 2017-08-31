package com.calintat.sensors.api

import android.view.View
import com.calintat.sensors.recycler.LoggerAdapter
import com.calintat.sensors.utils.AnkoFragment

/**
 * Fragment which acts as a data logger for sensor snapshots.
 */
class Logger : AnkoFragment<Logger>() {

    override val me get() = this

    override val ui get() = LoggerUI

    /**
     * Custom data structure for sensor snapshots.
     */
    data class Snapshot(private val timestamp: Long, val data: List<Float>) {

        fun printTimeInSeconds() = (timestamp / 1000f).toString()
    }

    /**
     * Adapter for the recycler view.
     */
    val adapter = LoggerAdapter()

    /**
     * Returns whether the logger is empty.
     */
    val isEmpty get() = adapter.items.isEmpty()

    /**
     * Time in milliseconds of the first entry.
     */
    private val initTimeMillis by lazy { System.currentTimeMillis() }

    /**
     * Returns a time in milliseconds relative to the initial time.
     */
    private val timestamp get() = System.currentTimeMillis() - initTimeMillis

    /**
     * Records a new entry to the logger. The entry must be an array of floats.
     */
    fun add(data: FloatArray) {

        adapter.add(Snapshot(timestamp, data.toList())); view.visibility = View.VISIBLE
    }
}