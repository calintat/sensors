package com.calintat.sensors.ui

import android.support.annotation.IdRes
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.calintat.sensors.R
import org.jetbrains.anko.*

object ListItem : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        linearLayout {

            lparams(width = matchParent, height = wrapContent)

            orientation = LinearLayout.HORIZONTAL

            verticalPadding = dip(8)

            createTextView(R.id.logger_list_item_time)

            createTextView(R.id.logger_list_item_data)
        }
    }

    internal fun _LinearLayout.createTextView(@IdRes id: Int): TextView {

        return textView {

            this.id = id; gravity = Gravity.CENTER

        }.lparams(width = 0, height = matchParent, weight = 1f)
    }
}