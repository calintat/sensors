package com.calintat.sensors.ui

import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Gravity
import com.calintat.sensors.R
import com.calintat.sensors.activities.MainActivity
import com.calintat.sensors.api.Item
import com.calintat.sensors.utils.AnkoProperties.overflowIconResource
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.navigationView

class MainUI : AnkoComponent<MainActivity> {

    internal lateinit var drawerLayout: DrawerLayout

    internal lateinit var toolbar: Toolbar

    internal lateinit var navigationView: NavigationView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

        include<DrawerLayout>(R.layout.drawer_layout) {

            drawerLayout = this

            coordinatorLayout {

                lparams(width = matchParent, height = wrapContent)

                toolbar {

                    toolbar = this

                    backgroundResource = R.color.primary

                    inflateMenu(R.menu.action)

                    overflowIconResource = R.drawable.ic_action_overflow

                    setOnMenuItemClickListener { owner.onMenuItemClick(it); true }

                    navigationIconResource = R.drawable.ic_action_menu

                    setNavigationOnClickListener { drawerLayout.openDrawer(navigationView) }

                }.lparams(width = matchParent, height = dimen(R.dimen.app_bar_height))

                verticalLayout {

                    cardView {

                        id = R.id.fragment_sensor

                        elevation = dip(4).toFloat()

                    }.lparams(width = matchParent, height = dip(128)) {

                        margin = dip(16)
                    }

                    cardView {

                        id = R.id.fragment_logger

                        elevation = dip(4).toFloat()

                    }.lparams(width = matchParent, height = 0, weight = 1f) {

                        bottomMargin = dip(44); horizontalMargin = dip(16)
                    }

                }.lparams(width = matchParent, height = matchParent) {

                    topMargin = dip(64)
                }

                floatingActionButton {

                    imageResource = R.drawable.ic_action_add

                    size = FloatingActionButton.SIZE_NORMAL

                    setOnClickListener { owner.addToLogger() }

                }.lparams(width = wrapContent, height = wrapContent) {

                    gravity = Gravity.BOTTOM or Gravity.CENTER; bottomMargin = dip(16)
                }
            }

            navigationView = navigationView {

                inflateMenu(R.menu.navigation)

                menu.itemsSequence().forEach {

                    it.isVisible = Item.get(it.itemId)?.isAvailable(ctx) ?: true
                }

                setNavigationItemSelectedListener { owner.navigationItemSelected(it); true }

                layoutParams = DrawerLayout.LayoutParams(wrapContent, matchParent, Gravity.START)
            }
        }
    }
}