package com.calintat.sensors.activities

import android.content.Context
import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceGroup
import android.support.v7.app.AppCompatActivity
import com.calintat.alps.*
import com.calintat.sensors.R
import com.calintat.sensors.api.Item
import com.calintat.sensors.ui.SettingsUI
import org.jetbrains.anko.ctx
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        SettingsUI.setContentView(this)

        populateWithPreferences(R.id.container) {

            themePreference()

            shortcutsPreference()

            aboutPreferenceCategory()
        }
    }

    internal fun PreferenceGroup.themePreference() {

        listPreference(key = "pref_theme") {

            titleResource = R.string.pref_theme

            summary = "%s"

            entries = arrayOf("Auto", "Dark", "Light")

            entryValues = arrayOf("0", "1", "2")

            onChange { toast(R.string.msg_app_restart_required); true }
        }
    }

    internal fun PreferenceGroup.shortcutsPreference() {

        if (Build.VERSION.SDK_INT < 25) return

        val shortcutManager = getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

        val items = Item.values().filter { it.isAvailable(ctx) }

        val names = items.map { getString(it.label) }

        val shortcutIds = items.map { it.shortcutId }

        multiSelectListPreference {

            titleResource = R.string.pref_shortcuts

            values = shortcutManager.dynamicShortcuts.map { it.id }.toSet()

            entries = names.toTypedArray()

            entryValues = shortcutIds.toTypedArray()

            onChange {

                if (it.size <= 4) {

                    shortcutManager.dynamicShortcuts = it.mapNotNull { Item.get(it)?.buildShortcut(ctx) }

                    true
                }
                else {

                    toast(R.string.err_shortcuts)

                    false
                }
            }
        }
    }

    internal fun PreferenceGroup.aboutPreferenceCategory() {

        preferenceCategory {

            titleResource = R.string.pref_about

            preference {
                titleResource = R.string.pref_version
                summaryResource = R.string.app_version
                url = "market://details?id=com.calintat.sensors"
            }

            preference {
                titleResource = R.string.pref_developer
                summaryResource = R.string.app_developer
                url = "https://play.google.com/store/apps/dev?id=5526451977947367946"
            }
        }
    }
}