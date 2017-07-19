package com.calintat.sensors.api

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.annotation.IdRes
import com.calintat.sensors.utils.AnkoFragment
import org.jetbrains.anko.withArguments

/**
 * Fragment that displays the values of a given sensor.
 */
abstract class Sensor : AnkoFragment(), SensorEventListener {

    companion object Builder {

        fun build(@IdRes id: Int?): com.calintat.sensors.api.Sensor? {

            if (id == null) return null

            val item = Item.get(id)

            return when (item?.dimension) {

                1 -> Sensor1d().withArguments("id" to id)

                3 -> Sensor3d().withArguments("id" to id)

                else -> null
            }
        }
    }

    abstract fun onValuesChanged(newValues: FloatArray)

    var values: FloatArray? = null

    val item by lazy { Item.get(arguments.getInt("id"))!! }

    val FREQUENCY get() = 100000000

    override fun onPause() {

        super.onPause()

        item.unregisterListener(activity, this)
    }

    override fun onResume() {

        super.onResume()

        item.registerListener(activity, this, FREQUENCY)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        event?.values?.let { values = it; onValuesChanged(it) }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}