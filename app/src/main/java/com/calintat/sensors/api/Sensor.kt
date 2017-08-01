package com.calintat.sensors.api

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.annotation.IdRes
import android.widget.TextView
import com.calintat.alps.longPref
import com.calintat.sensors.utils.AnkoFragment
import org.jetbrains.anko.withArguments

/**
 * Fragment that displays the values of a given sensor.
 */
class Sensor : AnkoFragment<Sensor>(), SensorEventListener {

    companion object Builder {

        private const val ID = "com.calintat.sensors.api.ID"

        fun build(@IdRes id: Int?) = id?.let { Sensor().withArguments(ID to it) }
    }

    override val me get() = this

    override val ui get() = if (item.dimension == 1) Sensor1D else Sensor3D

    /**
     * Time in milliseconds when [values] was last updated.
     */
    var timestamp = 0L

    /**
     * Current sensor values that are being displayed.
     */
    var values: FloatArray? = null

    /**
     * List of text views which are used to display [values].
     */
    var textViews = listOf<TextView>()

    /**
     * The [Item] object for the sensor that will be displayed.
     */
    val item by lazy { checkNotNull(Item.get(arguments.getInt(ID))) }

    override fun onPause() {

        super.onPause()

        item.unregisterListener(activity, this)
    }

    override fun onResume() {

        super.onResume()

        item.registerListener(activity, this, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        event?.values?.let {

            val currentTime = System.currentTimeMillis()

            if (currentTime - timestamp >= 750) {

                timestamp = currentTime; values = it.copyOf(item.dimension)

                it.zip(textViews) { v, textView -> textView.text = "${v.toDouble()}" }
            }
        }
    }

    override fun onAccuracyChanged(sensor: android.hardware.Sensor?, accuracy: Int) {}
}