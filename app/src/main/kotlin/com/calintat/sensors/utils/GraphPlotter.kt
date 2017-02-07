package com.calintat.sensors.utils

import android.graphics.Color
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import org.jetbrains.annotations.Mutable

object GraphPlotter {

    fun plot(graphView: GraphView, entries: List<Pair<Float, FloatArray>>) {

        val size = entries[0].second.size

        var series1: PointsGraphSeries<DataPoint>? = null

        var series2: PointsGraphSeries<DataPoint>? = null

        val series3: PointsGraphSeries<DataPoint>?

        if (size == 3) {

            val items1 = entries.map { DataPoint(it.first.toDouble(), it.second[0].toDouble()) }

            val items2 = entries.map { DataPoint(it.first.toDouble(), it.second[1].toDouble()) }

            val items3 = entries.map { DataPoint(it.first.toDouble(), it.second[2].toDouble()) }

            series1 = PointsGraphSeries(items1.asReversed().toTypedArray())

            series2 = PointsGraphSeries(items2.asReversed().toTypedArray())

            series3 = PointsGraphSeries(items3.asReversed().toTypedArray())

            series1.color = Color.RED

            series2.color = Color.GREEN

            series3.color = Color.BLUE


        }
        else {

            val items1 = entries.map { DataPoint(it.first.toDouble(), it.second[0].toDouble()) }

            series1 = PointsGraphSeries(items1.asReversed().toTypedArray())

            series1.shape = PointsGraphSeries.Shape.POINT

            series1.color = Color.RED

            graphView.addSeries(series1)

        }

        series1?.let { graphView.addSeries(it) }

        series2?.let { graphView.addSeries(it) }

        series1?.let { graphView.addSeries(it) }

    }

    private fun prepareData(entries: List<Pair<Float, FloatArray>>) {


    }
}