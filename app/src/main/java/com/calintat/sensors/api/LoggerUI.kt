package com.calintat.sensors.api

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
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

            visibility = View.INVISIBLE

            linearLayout {

                textView(text = R.string.time)

                textView(text = R.string.data)

            }.applyRecursively {

                if (it is TextView) {

                    it.lparams(width = 0, height = matchParent, weight = 1f)

                    it.gravity = Gravity.CENTER

                    it.textAppearance = R.style.TextAppearance_AppCompat_Subhead
                }

            }.lparams(width = matchParent, height = dip(56))

            recyclerView {

                adapter = owner.adapter

                layoutManager = LinearLayoutManager(ctx)

                bottomPadding = dip(16); clipToPadding = false

            }.lparams(width = matchParent, height = 0, weight = 1f)
        }
    }
}