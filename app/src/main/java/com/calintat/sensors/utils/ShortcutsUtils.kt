package com.calintat.sensors.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import com.calintat.sensors.activities.MainActivity
import com.calintat.sensors.api.Item
import org.jetbrains.anko.intentFor

@TargetApi(25)
object ShortcutsUtils {

    val Context.shortcutManager
        get() = getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

    var Context.shortcuts: List<String>

        get() = shortcutManager.dynamicShortcuts.map { it.id }

        set(value) { shortcutManager.dynamicShortcuts = value.mapNotNull { Item.get(it)?.let { buildShortcut(it) } } }

    fun Context.buildShortcut(item: Item): ShortcutInfo {

        val builder = ShortcutInfo.Builder(this, item.shortcutId)

        builder.setShortLabel(resources.getString(item.label))

        builder.setIcon(Icon.createWithResource(this, item.shortcutIcon))

        val intent = intentFor<MainActivity>("shortcut_id" to item.shortcutId)

        builder.setIntent(intent.setAction(Intent.ACTION_MAIN))

        builder.setRank(item.ordinal)

        return builder.build()
    }
}