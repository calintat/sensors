package com.calintat.sensors.activities

import android.app.Fragment
import android.net.Uri
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Gravity
import android.view.MenuItem
import com.calintat.alps.getInt
import com.calintat.alps.putInt
import com.calintat.sensors.R
import com.calintat.sensors.api.Item
import com.calintat.sensors.api.Logger
import com.calintat.sensors.api.Sensor
import com.calintat.sensors.ui.About
import com.calintat.sensors.ui.MainUI
import com.calintat.sensors.utils.InAppBillingHelper
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    internal val KEY_ID = "com.calintat.sensors.KEY_ID"

    internal val ui by lazy { MainUI }

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

    internal var billingHelper: InAppBillingHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setTheme()

        ui.setContentView(this)

        init(savedInstanceState)

        billingHelper = InAppBillingHelper(this)
    }

    override fun onBackPressed() {

        when {

            ui.drawerLayout.isDrawerOpen(Gravity.START) -> ui.drawerLayout.closeDrawers()

            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {

        super.onDestroy()

        billingHelper?.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        id?.let { outState.putInt(KEY_ID, it) }

        super.onSaveInstanceState(outState)
    }

    internal fun setTheme() {

        AppCompatDelegate.setDefaultNightMode(getInt("pref_theme"))
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

        val defaultId = getInt(KEY_ID).takeIf { Item.isIdSafe(it) } ?: defaultItem.id

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

            R.id.navigation_donation -> supportDevelopment()

            else -> id = item.itemId
        }
    }

    internal fun addToLogger() {

        sensor?.values?.let { logger?.add(it); refreshToolbarMenu() }
    }

    internal fun actionClear() {

        logger = Logger() // replace with new empty logger
    }

    internal fun actionAbout() {

        Item.get(id)?.let {

            alert { customView = About.createView(AnkoContext.create(ctx, it)) }.show()
        }
    }

    internal fun reportIssue() {

        val builder = CustomTabsIntent.Builder()

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.primary))

        builder.build().launchUrl(this, Uri.parse("https://github.com/calintat/sensors/issues"))
    }

    internal fun supportDevelopment() {

        val title = getString(R.string.navigation_donation)

        val items = listOf("£0.99", "£1.99", "£2.99", "£3.99", "£4.99", "£9.99")

        selector(title, items) { _, index -> billingHelper?.makeDonation("donation$index") }
    }

    internal fun refreshToolbarMenu() {

        ui.toolbar.menu.findItem(R.id.action_clear).isVisible = logger?.isEmpty?.not() ?: false
    }

    internal fun replace(@IdRes containerViewId: Int, fragment: Fragment?) {

        fragment?.let { fragmentManager.beginTransaction().replace(containerViewId, it).commit() }
    }
}