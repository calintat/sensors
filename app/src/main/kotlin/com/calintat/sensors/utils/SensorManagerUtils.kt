package com.calintat.sensors.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import com.calintat.sensors.R

object SensorManagerUtils {

    data class SensorItem(val type: Int, @IdRes val id: Int, @StringRes val name: Int, @StringRes val unit: Int, val size: Int = 3)

    //----------------------------------------------------------------------------------------------

    fun getIds() = idMap.keys

    fun getItem(@IdRes id: Int) = idMap[id]!!

    fun getType(@IdRes id: Int) = getItem(id).type

    fun getDefId(context: Context) = idMap.keys.firstOrNull { isSensorAvailable(context, it) }

    fun getManager(context: Context) = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    fun isSensorAvailable(context: Context, @IdRes id: Int) = getManager(context).getDefaultSensor(getType(id)) != null

    //----------------------------------------------------------------------------------------------

    private val ACCELEROMETER = SensorItem(Sensor.TYPE_ACCELEROMETER, R.id.navigation_accelerometer, R.string.navigation_accelerometer, R.string.metre_per_second_squared)

    private val AMBIENT_TEMPERATURE = SensorItem(Sensor.TYPE_AMBIENT_TEMPERATURE, R.id.navigation_ambient_temperature, R.string.navigation_ambient_temperature, R.string.celsius, 1)

    private val GRAVITY = SensorItem(Sensor.TYPE_GRAVITY, R.id.navigation_gravity, R.string.navigation_gravity, R.string.metre_per_second_squared)

    private val GYROSCOPE = SensorItem(Sensor.TYPE_GYROSCOPE, R.id.navigation_gyroscope, R.string.navigation_gyroscope, R.string.radian_per_second)

    private val LIGHT = SensorItem(Sensor.TYPE_LIGHT, R.id.navigation_light, R.string.navigation_light, R.string.lux, 1)

    private val LINEAR_ACCELERATION = SensorItem(Sensor.TYPE_LINEAR_ACCELERATION, R.id.navigation_linear_acceleration, R.string.navigation_linear_acceleration, R.string.metre_per_second_squared)

    private val MAGNETIC_FIELD = SensorItem(Sensor.TYPE_MAGNETIC_FIELD, R.id.navigation_magnetic_field, R.string.navigation_magnetic_field, R.string.microtesla)

    private val PRESSURE = SensorItem(Sensor.TYPE_PRESSURE, R.id.navigation_pressure, R.string.navigation_pressure, R.string.millibar, 1)

    private val PROXIMITY = SensorItem(Sensor.TYPE_PROXIMITY, R.id.navigation_proximity, R.string.navigation_proximity, R.string.centimetre, 1)

    private val RELATIVE_HUMIDITY = SensorItem(Sensor.TYPE_RELATIVE_HUMIDITY, R.id.navigation_relative_humidity, R.string.navigation_relative_humidity, R.string.percentage, 1)

    private val ROTATION_VECTOR = SensorItem(Sensor.TYPE_ROTATION_VECTOR, R.id.navigation_rotation_vector, R.string.navigation_rotation_vector, R.string.unitless)

    //----------------------------------------------------------------------------------------------

    private val idMap = hashMapOf(

            R.id.navigation_accelerometer to ACCELEROMETER,

            R.id.navigation_ambient_temperature to AMBIENT_TEMPERATURE,

            R.id.navigation_gravity to GRAVITY,

            R.id.navigation_gyroscope to GYROSCOPE,

            R.id.navigation_light to LIGHT,

            R.id.navigation_linear_acceleration to LINEAR_ACCELERATION,

            R.id.navigation_magnetic_field to MAGNETIC_FIELD,

            R.id.navigation_pressure to PRESSURE,

            R.id.navigation_proximity to PROXIMITY,

            R.id.navigation_relative_humidity to RELATIVE_HUMIDITY,

            R.id.navigation_rotation_vector to ROTATION_VECTOR
    )
}