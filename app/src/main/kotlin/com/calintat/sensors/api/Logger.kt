package com.calintat.sensors.api

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.calintat.sensors.R
import com.calintat.sensors.api.Snapshot.Companion.snap
import com.calintat.sensors.recycler.Adapter
import com.calintat.sensors.ui.MainUI.textAppearance
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Fragment which acts as a data logger for sensor snapshots.
 */
class Logger : Fragment() {

    private object LoggerComponent : AnkoComponent<Logger> {

        override fun createView(ui: AnkoContext<Logger>) = with(ui) {

            verticalLayout {

                linearLayout {

                    orientation = LinearLayout.HORIZONTAL

                    textView {

                        textResource = R.string.time

                        gravity = Gravity.CENTER

                        textAppearance = R.style.TextAppearance_AppCompat_Subhead

                    }.lparams(width = 0, height = matchParent, weight = 1f)

                    textView {

                        textResource = R.string.data

                        gravity = Gravity.CENTER

                        textAppearance = R.style.TextAppearance_AppCompat_Subhead

                    }.lparams(width = 0, height = matchParent, weight = 1f)

                }.lparams(width = matchParent, height = dip(56))

                recyclerView {

                    adapter = owner.adapter

                    layoutManager = LinearLayoutManager(ctx)

                }.lparams(width = matchParent, height = 0, weight = 1f)
            }
        }
    }

    private val adapter by lazy { Adapter(activity) }

    private val currentTime get() = (System.currentTimeMillis() - initialTime) / 1000f

    var isEmpty = true

    val entries get() = adapter.items

    val initialTime by lazy { System.currentTimeMillis() }

    operator fun plusAssign(data: List<Float>?) {

        data?.let { isEmpty = false; adapter.add(currentTime snap it) }
    }

    override fun onCreateView(i: LayoutInflater?, c: ViewGroup?, s: Bundle?): View {

        return LoggerComponent.createView(AnkoContext.create(activity, this))
    }
}