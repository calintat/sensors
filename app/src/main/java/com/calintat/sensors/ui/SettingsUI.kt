package com.calintat.sensors.ui

import com.calintat.sensors.R
import com.calintat.sensors.activities.SettingsActivity
import com.calintat.sensors.utils.AnkoProperties.titleTextColorResource
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.verticalLayout

object SettingsUI : AnkoComponent<SettingsActivity> {

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