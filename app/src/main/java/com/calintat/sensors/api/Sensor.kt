package com.calintat.sensors.api

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.annotation.IdRes
import android.widget.TextView
import com.calintat.sensors.utils.AnkoFragment
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sensorManager
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

        ctx.sensorManager.unregisterListener(this)
    }

    override fun onResume() {

        super.onResume()

        val sensor = item.getDefaultSensor(ctx)

        ctx.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 750)
    }

    override fun onAccuracyChanged(sensor: android.hardware.Sensor?, accuracy: Int) {

        /* do nothing */
    }

    override fun onSensorChanged(event: SensorEvent?) {

        values = event?.values?.copyOf(item.dimension)

        val format = if (item.dimension == 1) "%.1f" else "%.2f"

        values?.zip(textViews) { value, textView -> textView.text = String.format(format, value) }
    }
}