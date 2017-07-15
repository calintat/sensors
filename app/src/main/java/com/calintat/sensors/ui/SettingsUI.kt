package com.calintat.sensors.ui

import android.support.v7.widget.Toolbar
import com.calintat.sensors.R
import com.calintat.sensors.activities.SettingsActivity
import com.calintat.sensors.utils.AnkoUtils.titleTextColorResource
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

class SettingsUI : AnkoComponent<SettingsActivity> {

    override fun createView(ui: AnkoContext<SettingsActivity>) = with(ui) {

        coordinatorLayout {

            fitsSystemWindows = true

            verticalLayout {

                appBarLayout {

                     toolbar {

                        navigationIconResource = R.drawable.ic_action_back

                        setNavigationOnClickListener { owner.finish() }

                        titleResource = R.string.navigation_settings

                        titleTextColorResource = R.color.white
                    }
                }

                frameLayout { id = R.id.container }
            }
        }
    }
}