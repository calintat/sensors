package com.calintat.sensors.api

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.LinearLayout
import com.calintat.sensors.R
import com.calintat.sensors.utils.AnkoProperties.textAppearance
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Anko UI for the [Logger] fragment.
 */
object LoggerUI : AnkoComponent<Logger> {

    override fun createView(ui: AnkoContext<Logger>) = with(ui) {

        verticalLayout {

            linearLayout {

                lparams(width = matchParent, height = dip(56))

                orientation = LinearLayout.HORIZONTAL

                textView(text = R.string.time) {

                    gravity = Gravity.CENTER

                    textAppearance = R.style.TextAppearance_AppCompat_Subhead

                }.lparams(width = 0, height = matchParent, weight = 1f)

                textView(text = R.string.data) {

                    gravity = Gravity.CENTER

                    textAppearance = R.style.TextAppearance_AppCompat_Subhead

                }.lparams(width = 0, height = matchParent, weight = 1f)
            }

            recyclerView {

                lparams(width = matchParent, height = 0, weight = 1f)

                adapter = owner.adapter

                layoutManager = LinearLayoutManager(ctx)
            }
        }
    }
}