package com.calintat.sensors.api

import android.content.Context
import android.hardware.SensorEventListener
import com.calintat.sensors.R
import org.jetbrains.anko.sensorManager

enum class Item(val sensor: Sensor, val id: Int, val label: Int, val shortcutIcon: Int) {

    ACCELEROMETER(

            sensor = Sensor.ACCELEROMETER,

            id = R.id.navigation_accelerometer,

            label = R.string.navigation_accelerometer,

            shortcutIcon = R.drawable.ic_shortcut_accelerometer
    ),

    AMBIENT_TEMPERATURE(

            sensor = Sensor.AMBIENT_TEMPERATURE,

            id = R.id.navigation_ambient_temperature,

            label = R.string.navigation_ambient_temperature,

            shortcutIcon = R.drawable.ic_shortcut_ambient_temperature
    ),

    GRAVITY(

            sensor = Sensor.GRAVITY,

            id = R.id.navigation_gravity,

            label = R.string.navigation_gravity,

            shortcutIcon = R.drawable.ic_shortcut_gravity
    ),

    GYROSCOPE(

            sensor = Sensor.GYROSCOPE,

            id = R.id.navigation_gyroscope,

            label = R.string.navigation_gyroscope,

            shortcutIcon = R.drawable.ic_shortcut_gyroscope
    ),

    LIGHT(

            sensor = Sensor.LIGHT,

            id = R.id.navigation_light,

            label = R.string.navigation_light,

            shortcutIcon = R.drawable.ic_shortcut_light
    ),

    LINEAR_ACCELERATION(

            sensor = Sensor.LINEAR_ACCELERATION,

            id = R.id.navigation_linear_acceleration,

            label = R.string.navigation_linear_acceleration,

            shortcutIcon = R.drawable.ic_shortcut_linear_acceleration
    ),

    MAGNETIC_FIELD(

            sensor = Sensor.MAGNETIC_FIELD,

            id = R.id.navigation_magnetic_field,

            label = R.string.navigation_magnetic_field,

            shortcutIcon = R.drawable.ic_shortcut_magnetic_field
    ),

    PRESSURE(

            sensor = Sensor.PRESSURE,

            id = R.id.navigation_pressure,

            label = R.string.navigation_pressure,

            shortcutIcon = R.drawable.ic_shortcut_pressure
    ),

    PROXIMITY(

            sensor = Sensor.PROXIMITY,

            id = R.id.navigation_proximity,

            label = R.string.navigation_proximity,

            shortcutIcon = R.drawable.ic_shortcut_proximity
    ),

    RELATIVE_HUMIDITY(

            sensor = Sensor.RELATIVE_HUMIDITY,

            id = R.id.navigation_relative_humidity,

            label = R.string.navigation_relative_humidity,

            shortcutIcon = R.drawable.ic_shortcut_relative_humidity
    ),

    ROTATION_VECTOR(

            sensor = Sensor.ROTATION_VECTOR,

            id = R.id.navigation_rotation_vector,

            label = R.string.navigation_rotation_vector,

            shortcutIcon = R.drawable.ic_shortcut_rotation_vector
    );

    companion object Utils {

        private val items by lazy { values().map { it.id to it }.toMap() }

        fun getItemOrNull(id: Int) = items[id]

        fun getItem(id: Int) = items[id] ?: throw Exception("Unknown identifier")

        val Context.defaultItem get() = values().firstOrNull { it.isAvailable(this) }
    }

    /**
     * The rank of the shortcut that's used by the launcher app to sort shortcuts.
     */
    val rank get() = ordinal

    /**
     * The string resource id for the measurement unit of this item's sensor.
     */
    val unit get() = sensor.unit

    /**
     * The dimension of this item's sensor (should be either 1 or 3).
     */
    val dimension get() = sensor.dimension

    /**
     * Use this method to get the default sensor for this item in a given context.
     *
     * @return The default sensor matching the requested type or null if none exists.
     */
    fun getDefaultSensor(context: Context): android.hardware.Sensor? {

        return context.sensorManager.getDefaultSensor(sensor.type)
    }

    /**
     * Return whether this item has any available sensors in a given context.
     */
    fun isAvailable(context: Context) = getDefaultSensor(context) != null

    /**
     * Register a listener for the default sensor at a given sampling period.
     *
     * @param frequency The rate in microseconds that sensor events will be delivered at.
     */
    fun registerListener(context: Context, listener: SensorEventListener, frequency: Int) {

        context.sensorManager.registerListener(listener, getDefaultSensor(context), frequency)
    }
}