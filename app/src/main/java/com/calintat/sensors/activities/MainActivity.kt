package com.calintat.sensors.activities

import android.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.calintat.sensors.R
import com.calintat.sensors.api.Item
import com.calintat.sensors.api.Logger
import com.calintat.sensors.api.Sensor
import com.calintat.sensors.ui.About
import com.calintat.sensors.ui.MainUI
import com.github.calintat.getInt
import com.github.calintat.getString
import com.github.calintat.putInt
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    internal val KEY_ID = "com.calintat.sensors.KEY_ID"

    internal val ui by lazy { MainUI() }

    internal var id: Int? = null

        set(value) {

            field = value

            putInt(KEY_ID, value)

            logger = Logger()

            sensor = Sensor.build(value)
        }

    internal var sensor: Sensor? = null

        set(value) {

            field = value

            replace(R.id.fragment_sensor, value)
        }

    internal var logger: Logger? = null

        set(value) {

            field = value

            refreshToolbarMenu()

            replace(R.id.fragment_logger, value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        init(savedInstanceState); setTheme()
    }

    override fun onBackPressed() {

        when {

            ui.drawerLayout.isDrawerOpen(ui.navigationView) -> ui.drawerLayout.closeDrawers()

            else -> super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        id?.let { outState.putInt(KEY_ID, it) }
        
        super.onSaveInstanceState(outState)
    }

    internal fun setTheme() {

        AppCompatDelegate.setDefaultNightMode(getString("pref_theme", "1").toInt())
    }

    internal fun init(savedInstanceState: Bundle?) {

        val defaultItem = Item.firstAvailableItem(this)

        if (defaultItem == null) { /* error: there are no sensors available */

            alert {

                titleResource = R.string.err_no_sensors_available

                positiveButton("Exit") { finish() }

                onCancelled { finish() }

                show()
            }

            return
        }

        val defaultId = getInt(KEY_ID, defaultItem.id)

        if (savedInstanceState == null) { /* opened from launcher or app shortcut */

            id = Item.get(intent)?.id ?: defaultId
        }
        else { /* orientation change, activity resumed, etc */

            id = savedInstanceState.getInt(KEY_ID, defaultId)
        }
    }

    internal fun onMenuItemClick(item: MenuItem) {

        when (item.itemId) {

            R.id.action_clear -> actionClear()

            R.id.action_about -> actionAbout()
        }
    }

    internal fun navigationItemSelected(item: MenuItem) {

        ui.drawerLayout.closeDrawers()

        when (item.itemId) {

            R.id.navigation_settings -> startActivity<SettingsActivity>()

            R.id.navigation_feedback -> reportIssue()

            else -> id = item.itemId
        }
    }

    internal fun addToLogger() {

        sensor?.values?.let { logger?.log(it); refreshToolbarMenu() }
    }

    internal fun actionClear() {

        logger = Logger() // replace with new empty logger
    }

    internal fun actionAbout() {

        Item.get(id)?.let {

            alert { customView = About().createView(AnkoContext.create(ctx, it)) }.show()
        }
    }

    internal fun reportIssue() {

        val builder = CustomTabsIntent.Builder()

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.primary))

        builder.build().launchUrl(this, Uri.parse("https://github.com/calintat/sensors/issues"))
    }

    internal fun refreshToolbarMenu() {

        ui.toolbar.menu.findItem(R.id.action_clear).isVisible = logger?.isNotEmpty ?: false
    }

    internal fun replace(@IdRes containerViewId: Int, fragment: Fragment?) {

        fragment?.let { fragmentManager.beginTransaction().replace(containerViewId, it).commit() }
    }
}