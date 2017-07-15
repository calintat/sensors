package com.calintat.sensors.api

import android.support.annotation.StringRes
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoUtils.textAppearance
import org.jetbrains.anko.*

/**
 * Implementation of the [Sensor] class for 3-dimensional sensors.
 */
class Sensor3d : Sensor() {

    internal var fieldX: TextView? = null

    internal var fieldY: TextView? = null

    internal var fieldZ: TextView? = null

    override fun onValuesChanged(newValues: FloatArray) {

        fieldX?.text = newValues[0].toString()

        fieldY?.text = newValues[1].toString()

        fieldZ?.text = newValues[2].toString()
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        linearLayout {

            lparams(width = matchParent, height = matchParent)

            orientation = LinearLayout.HORIZONTAL

            isBaselineAligned = false

            gravity = Gravity.CENTER

            padding = dip(16)

            createField(R.string.x_axis) { fieldX = this }

            createField(R.string.y_axis) { fieldY = this }

            createField(R.string.z_axis) { fieldZ = this }
        }
    }

    internal fun ViewManager.createField(@StringRes textResId: Int, init: TextView.() -> Unit) {

        verticalLayout {

            lparams(width = 0, height = matchParent, weight = 1f)

            gravity = Gravity.CENTER

            textView(text = textResId) {

                textAppearance = R.style.TextAppearance_AppCompat_Caption

            }.lparams(width = wrapContent, height = wrapContent)

            textView {

                init()

                maxLines = 1

                textAppearance = R.style.TextAppearance_AppCompat_Title

            }.lparams(width = wrapContent, height = wrapContent)

            textView(text = item.unit) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2

            }.lparams(width = wrapContent, height = wrapContent)
        }
    }
}