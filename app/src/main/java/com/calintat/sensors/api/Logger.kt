package com.calintat.sensors.api

import com.calintat.sensors.recycler.Adapter
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
    data class Snapshot(val timestamp: Long, val data: List<Float>) {

        fun printTimeInSeconds() = (timestamp / 1000f).toString()
    }

    /**
     * Adapter for the recycler view.
     */
    val adapter by lazy { Adapter(activity) }

    /**
     * Returns whether the logger is empty.
     */
    val isEmpty get() = !isAdded || adapter.items.isEmpty()

    /**
     * Time in milliseconds of the first entry.
     */
    val initialTimeMillis by lazy { System.currentTimeMillis() }

    /**
     * Returns a time in milliseconds relative to the initial time.
     */
    val timestamp get() = System.currentTimeMillis() - initialTimeMillis

    /**
     * Records a new entry to the logger. The entry must be an array of floats.
     */
    fun add(data: FloatArray) = adapter.add(Snapshot(timestamp, data.toList()))
}