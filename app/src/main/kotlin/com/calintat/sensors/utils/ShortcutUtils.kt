package com.calintat.sensors.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.support.annotation.IdRes
import com.calintat.sensors.R
import com.calintat.sensors.activities.MainActivity
import org.jetbrains.anko.intentFor

@TargetApi(25)
object ShortcutUtils {

    /**
     * Retrieve navigation identifier from a shortcut identifier.
     */
    internal fun retrieveId(shortcutId: String): Int? {

        return shortcuts.firstOrNull { it.shortcutId == shortcutId }?.id
    }

    /**
     * Retrieve shortcut identifier from a navigation identifier.
     */
    internal fun retrieveShortcutId(id: Int): String? {

        return shortcuts.firstOrNull { it.id == id }?.shortcutId
    }

    /**
     * Return the set of identifiers of the dynamic shortcuts from the caller app.
     */
    internal fun getShortcuts(context: Context) = with(context) {

        val shortcutManager = getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

        shortcutManager.dynamicShortcuts.mapNotNull { retrieveId(it.id) }.toSet()
    }

    /**
     * Build and set dynamic shortcuts from a set of identifiers.
     */
    internal fun setShortcuts(context: Context, ids: Set<Int>) = with(context) {

        val shortcutManager = getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

        shortcutManager.dynamicShortcuts = ids.mapNotNull { buildShortcut(this, it) }
    }

    /**
     * Build a shortcut from an identifier inside the given context.
     */
    internal fun buildShortcut(context: Context, id: Int) = retrieveShortcutId(id)?.let {

        val item = SensorUtils.get(id)

        ShortcutInfo.Builder(context, it)

                .setShortLabel(context.resources.getString(item.name))

                .setIcon(Icon.createWithResource(context, item.shortcutIcon))

                .setIntent(context.intentFor<MainActivity>().setAction(it))

                .setRank(item.rank)

                .build()
    }

    /**
     * Custom pair structure for the entries of the mapping below.
     */
    private data class Shortcut(@IdRes val id: Int, val shortcutId: String)

    /**
     * Invertible mapping between navigation identifiers and shortcut identifiers.
     */
    private val shortcuts = hashSetOf(

            Shortcut(R.id.navigation_accelerometer, "com.calintat.sensors.SHORTCUT_ACCELEROMETER"),

            Shortcut(R.id.navigation_ambient_temperature, "com.calintat.sensors.SHORTCUT_AMBIENT_TEMPERATURE"),

            Shortcut(R.id.navigation_gravity, "com.calintat.sensors.SHORTCUT_GRAVITY"),

            Shortcut(R.id.navigation_gyroscope, "com.calintat.sensors.SHORTCUT_GYROSCOPE"),

            Shortcut(R.id.navigation_light, "com.calintat.sensors.SHORTCUT_LIGHT"),

            Shortcut(R.id.navigation_linear_acceleration, "com.calintat.sensors.SHORTCUT_LINEAR_ACCELERATION"),

            Shortcut(R.id.navigation_magnetic_field, "com.calintat.sensors.SHORTCUT_MAGNETIC_FIELD"),

            Shortcut(R.id.navigation_pressure, "com.calintat.sensors.SHORTCUT_PRESSURE"),

            Shortcut(R.id.navigation_proximity, "com.calintat.sensors.SHORTCUT_PROXIMITY"),

            Shortcut(R.id.navigation_relative_humidity, "com.calintat.sensors.SHORTCUT_RELATIVE_HUMIDITY"),

            Shortcut(R.id.navigation_rotation_vector, "com.calintat.sensors.SHORTCUT_ROTATION_VECTOR")
    )
}