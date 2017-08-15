package com.calintat.sensors.api

import android.support.annotation.StringRes
import android.view.Gravity
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoProperties.textAppearance
import org.jetbrains.anko.*

/**
 * Anko UI for the [Sensor] fragment for 3-dimensional sensors.
 */
object Sensor3D : AnkoComponent<Sensor> {

    override fun createView(ui: AnkoContext<Sensor>) = with(ui) {

        linearLayout {

            lparams(width = matchParent, height = matchParent)

            orientation = LinearLayout.HORIZONTAL

            isBaselineAligned = false

            gravity = Gravity.CENTER

            padding = dip(16)

            createField(R.string.x_axis, owner.item.unit) { owner.textViews += this }

            createField(R.string.y_axis, owner.item.unit) { owner.textViews += this }

            createField(R.string.z_axis, owner.item.unit) { owner.textViews += this }
        }
    }

    private fun ViewManager.createField(@StringRes top: Int, @StringRes bottom: Int, init: TextView.() -> Unit) {

        verticalLayout {

            lparams(width = 0, height = matchParent, weight = 1f)

            gravity = Gravity.CENTER

            textView(text = top) {

                textAppearance = R.style.TextAppearance_AppCompat_Caption

            }.lparams(width = wrapContent, height = wrapContent)

            textView {

                init()

                maxLines = 1

                horizontalPadding = dip(10)

                textAppearance = R.style.TextAppearance_AppCompat_Title

            }.lparams(width = wrapContent, height = wrapContent)

            textView(text = bottom) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2

            }.lparams(width = wrapContent, height = wrapContent)
        }
    }
}