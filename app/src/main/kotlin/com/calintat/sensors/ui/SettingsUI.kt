package com.calintat.sensors.ui

import android.widget.Toolbar
import com.calintat.sensors.R
import com.calintat.sensors.activities.SettingsActivity
import com.github.calintat.Colorful
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

object SettingsUI : AnkoComponent<SettingsActivity> {

    internal lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<SettingsActivity>) = with(ui) {

        coordinatorLayout {

            fitsSystemWindows = true

            verticalLayout {

                appBarLayout {

                    toolbar = toolbar {

                        setTitleTextColor(Colorful.white)
                    }
                }

                frameLayout {

                    id = R.id.fragment
                }
            }
        }
    }
}