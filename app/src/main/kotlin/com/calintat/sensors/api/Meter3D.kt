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
 * Implementation of the [Meter] class for 3-dimensional sensors.
 */
class Meter3D : Meter() {

    var fieldX: TextView? = null

    var fieldY: TextView? = null

    var fieldZ: TextView? = null

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

            createTextField(R.string.x_axis) { fieldX = this }

            createTextField(R.string.y_axis) { fieldY = this }

            createTextField(R.string.z_axis) { fieldZ = this }
        }
    }

    fun ViewManager.createTextField(@StringRes textResId: Int, init: TextView.() -> Unit) {

        verticalLayout {

            lparams(width = 0, height = matchParent, weight = 1f)

            gravity = Gravity.CENTER

            textView(text = textResId) {

                textAppearance = R.style.TextAppearance_AppCompat_Caption
            }

            textView {

                init()

                maxLines = 1

                textAppearance = R.style.TextAppearance_AppCompat_Title
            }

            textView(text = item.unit) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2
            }
        }
    }
}