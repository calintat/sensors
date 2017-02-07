package com.calintat.sensors.utils

import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object CSVUtils {

    private val homeDir by lazy { Environment.getExternalStorageDirectory() }

    private val formatter by lazy { SimpleDateFormat("yyMMdd-HHmm", Locale.ENGLISH) }

    fun writeToCSV(timestamp: Long, values: Collection<Pair<Float, FloatArray>>) {

        val fileWrite = FileWriter(generateFile(timestamp))

        fileWrite.appendln("$timestamp")

        values.forEach { fileWrite.append(makeLine(it.first, it.second)) }

        fileWrite.close()
    }

    fun makeLine(time: Float, measurement: FloatArray): String {

        val buffer = StringBuffer()

        buffer.append(time)

        buffer.append(',')

        buffer.appendln(measurement.joinToString(separator = ","))

        return buffer.toString()
    }

    private fun generateFile(timestamp: Long): File {

        val directory = File(homeDir, "Sensors")

        directory.mkdir()

        return File(directory, generateName(timestamp))
    }

    private fun generateName(timestamp: Long) = "log-${formatter.format(timestamp)}.csv"
}