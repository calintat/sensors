package com.calintat.sensors.api

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.calintat.sensors.R
import com.calintat.sensors.api.Snapshot.Companion.snap
import com.calintat.sensors.recycler.Adapter
import com.calintat.sensors.utils.AnkoFragment
import com.calintat.sensors.utils.AnkoUtils.textAppearance
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Fragment which acts as a data logger for sensor snapshots.
 */
class Logger : AnkoFragment() {

    var adapter: Adapter? = null

    val isNotEmpty get() = adapter?.items?.isNotEmpty()

    val timestamp by lazy { System.currentTimeMillis() }

    fun log(xs: FloatArray) = adapter?.add(timestamp snap xs.toList())

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        adapter = Adapter(activity)

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

                adapter = this@Logger.adapter

                layoutManager = LinearLayoutManager(ctx)
            }
        }
    }
}