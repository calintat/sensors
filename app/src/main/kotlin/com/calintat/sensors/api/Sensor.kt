package com.calintat.sensors.api

import android.support.annotation.StringRes
import com.calintat.sensors.R

enum class Sensor(val type: Int, @StringRes val unit: Int, val dimension: Int) {

    ACCELEROMETER(

            type = android.hardware.Sensor.TYPE_ACCELEROMETER,

            unit = R.string.metre_per_second_squared,

            dimension = 3
    ),

    AMBIENT_TEMPERATURE(

            type = android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE,

            unit = R.string.celsius,

            dimension = 1
    ),

    GRAVITY(

            type = android.hardware.Sensor.TYPE_GRAVITY,

            unit = R.string.metre_per_second_squared,

            dimension = 3
    ),

    GYROSCOPE(

            type = android.hardware.Sensor.TYPE_GYROSCOPE,

            unit = R.string.radian_per_second,

            dimension = 3
    ),

    LIGHT(
            type = android.hardware.Sensor.TYPE_LIGHT,

            unit = R.string.lux,

            dimension = 1
    ),

    LINEAR_ACCELERATION(

            type = android.hardware.Sensor.TYPE_LINEAR_ACCELERATION,

            unit = R.string.metre_per_second_squared,

            dimension = 3
    ),

    MAGNETIC_FIELD(

            type = android.hardware.Sensor.TYPE_MAGNETIC_FIELD,

            unit = R.string.microtesla,

            dimension = 3
    ),

    PRESSURE(

            type = android.hardware.Sensor.TYPE_PRESSURE,

            unit = R.string.millibar,

            dimension = 1
    ),

    PROXIMITY(

            type = android.hardware.Sensor.TYPE_PROXIMITY,

            unit = R.string.centimetre,

            dimension = 1
    ),

    RELATIVE_HUMIDITY(

            type = android.hardware.Sensor.TYPE_RELATIVE_HUMIDITY,

            unit = R.string.percentage,

            dimension = 1
    ),

    ROTATION_VECTOR(

            type = android.hardware.Sensor.TYPE_ROTATION_VECTOR,

            unit = R.string.unitless,

            dimension = 3
    );
}