package com.calintat.sensors.ui

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.calintat.sensors.R
import com.github.calintat.Colorful
import org.jetbrains.anko.*
import java.util.*

object ListItem : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        linearLayout {

            lparams(width = matchParent, height = wrapContent)

            orientation = LinearLayout.HORIZONTAL

            verticalPadding = dip(8)

            textView {

                id = R.id.logger_list_item_time

                gravity = Gravity.CENTER

            }.lparams(width = 0, height = matchParent, weight = 1f)

            textView {

                id = R.id.logger_list_item_data

                gravity = Gravity.CENTER

            }.lparams(width = 0, height = matchParent, weight = 1f)
        }
    }
}