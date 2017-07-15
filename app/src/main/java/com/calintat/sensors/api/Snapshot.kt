package com.calintat.sensors.api

data class Snapshot(val time: Float, val data: List<Float>) {

    companion object {

        infix fun Long.snap(data: List<Float>): Snapshot {

            return Snapshot((System.currentTimeMillis() - this) / 1000f, data)
        }
    }
}