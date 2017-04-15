package com.calintat.sensors.api

data class Snapshot(val time: Float, val data: List<Float>) {

    companion object {

        infix fun Float.snap(data: List<Float>) = Snapshot(this, data)
    }
}