package com.calintat.sensors.api

import android.view.Gravity
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoProperties.textAppearance
import org.jetbrains.anko.*

/**
 * Anko UI for the [Sensor] fragment for 1-dimensional sensors.
 */
object Sensor1D : AnkoComponent<Sensor> {

    override fun createView(ui: AnkoContext<Sensor>) = with(ui) {

        verticalLayout {

            lparams(width = matchParent, height = matchParent)

            gravity = Gravity.CENTER

            owner.textViews += textView {

                maxLines = 1

                textAppearance = R.style.TextAppearance_AppCompat_Display2

            }.lparams(width = wrapContent, height = wrapContent)

            textView(text = owner.item.unit) {

                textAppearance = R.style.TextAppearance_AppCompat_Body2

            }.lparams(width = wrapContent, height = wrapContent)
        }
    }
}