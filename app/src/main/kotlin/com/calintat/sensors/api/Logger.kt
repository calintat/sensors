package com.calintat.sensors.api

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.calintat.sensors.R
import com.calintat.sensors.api.Snapshot.Companion.snap
import com.calintat.sensors.recycler.Adapter
import com.calintat.sensors.utils.AnkoUtils
import com.calintat.sensors.utils.AnkoUtils.textAppearance
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Fragment which acts as a data logger for sensor snapshots.
 */
class Logger : AnkoUtils.AnkoFragment() {

    var adapter: Adapter? = null

    val isEmpty get() = adapter?.items?.isEmpty()

    val timestamp by lazy { System.currentTimeMillis() }

    fun log(xs: List<Float>) = adapter?.add(timestamp snap xs)

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        adapter = Adapter(activity)

        verticalLayout {

            linearLayout {

                lparams(width = matchParent, height = dip(56))

                orientation = LinearLayout.HORIZONTAL

                textView(text = R.string.time) {

                    lparams(width = 0, height = matchParent, weight = 1f)

                    gravity = Gravity.CENTER

                    textAppearance = R.style.TextAppearance_AppCompat_Subhead

                }

                textView(text = R.string.data) {

                    lparams(width = 0, height = matchParent, weight = 1f)

                    gravity = Gravity.CENTER

                    textAppearance = R.style.TextAppearance_AppCompat_Subhead
                }
            }

            recyclerView {

                lparams(width = matchParent, height = 0, weight = 1f)

                adapter = this@Logger.adapter

                layoutManager = LinearLayoutManager(ctx)
            }
        }
    }
}