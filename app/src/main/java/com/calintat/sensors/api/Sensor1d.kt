package com.calintat.sensors.api

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoProperties.textAppearance
import org.jetbrains.anko.*

/**
 * Implementation of the [Sensor] class for 1-dimensional sensors.
 */
class Sensor1d : Sensor() {

    internal var field: TextView? = null

    override fun onValuesChanged(newValues: FloatArray) {

        field?.text = newValues[0].toString()
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        verticalLayout {

            lparams(width = matchParent, height = matchParent)

            gravity = Gravity.CENTER

            field = textView {

                maxLines = 1

                textAppearance = R.style.TextAppearance_AppCompat_Display2

            }.lparams(width = wrapContent, height = wrapContent)

            textView(text = item.unit) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2

            }.lparams(width = wrapContent, height = wrapContent)
        }
    }
}