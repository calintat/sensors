package com.calintat.sensors.api

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoUtils.textAppearance
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 * Implementation of the [Meter] class for 1-dimensional sensors.
 */
class Meter1D : Meter() {

    var field: TextView? = null

    override fun onValuesChanged(newValues: FloatArray) {

        field?.text = newValues[0].toString()
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        verticalLayout {

            lparams(width = matchParent, height = matchParent)

            gravity = Gravity.CENTER

            textView {

                field = this

                maxLines = 1

                textAppearance = R.style.TextAppearance_AppCompat_Display2
            }

            textView(text = item.unit) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2
            }
        }
    }
}