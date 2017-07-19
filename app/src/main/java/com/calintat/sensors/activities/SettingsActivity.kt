package com.calintat.sensors.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.calintat.alps.*
import com.calintat.sensors.R
import com.calintat.sensors.api.Item
import com.calintat.sensors.ui.SettingsUI
import com.calintat.sensors.utils.ShortcutsUtils.shortcuts
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        SettingsUI().setContentView(this)

        populateWithPreferences(R.id.container) {

            listPreference("pref_theme") {

                titleResource = R.string.pref_theme

                summary = "%s"

                entries = arrayOf("Auto", "Light", "Dark")

                entryValues = arrayOf("0", "1", "2")

                onChange { toast(R.string.msg_app_restart_required); true }
            }

            multiSelectListPreference {

                titleResource = R.string.pref_shortcuts

                values = shortcuts.toSet()

                entries = Item.values().map { resources.getString(it.label) }.toTypedArray()

                entryValues = Item.values().map { it.shortcutId }.toTypedArray()

                onChange { onShortcutsChanged(it) }
            }

            preferenceCategory {

                setTitle(R.string.pref_about)

                preference {

                    titleResource = R.string.pref_version

                    summaryResource = R.string.app_version

                    setUrl("market://details?id=com.calintat.sensors")
                }

                preference {

                    titleResource = R.string.pref_developer

                    summaryResource = R.string.app_developer

                    setUrl("https://play.google.com/store/apps/dev?id=5526451977947367946")
                }
            }
        }
    }

    internal fun onShortcutsChanged(new: Set<String>): Boolean {

        if (new.size <= 4) {

            shortcuts = new.toList()

            return true
        }
        else {

            toast(R.string.err_shortcuts)

            return false
        }
    }
}