package com.calintat.sensors.ui

import com.calintat.sensors.R
import com.calintat.sensors.api.Item
import com.calintat.sensors.utils.AnkoUtils.textAppearance
import org.jetbrains.anko.*

class About : AnkoComponent<Item> {

    override fun createView(ui: AnkoContext<Item>) = with(ui) {

        verticalLayout {

            padding = dip(16)

            val sensor = owner.getDefaultSensor(context)

            val unit = context.resources.getString(owner.unit)

            newEntry(R.string.about_name, sensor?.name.toString())

            newEntry(R.string.about_vendor, sensor?.vendor.toString())

            newEntry(R.string.about_version, sensor?.version.toString())

            newEntry(R.string.about_power, "${sensor?.power} mA")

            newEntry(R.string.about_resolution, "${sensor?.resolution} $unit")

            newEntry(R.string.about_maximum_range, "${sensor?.maximumRange} $unit")
        }
    }

    internal fun _LinearLayout.newEntry(title: Int, content: String) = verticalLayout {

        padding = dip(8)

        textView(text = title) {

            textAppearance = R.style.TextAppearance_AppCompat_Body2
        }

        textView(text = content) {

            textAppearance = R.style.TextAppearance_AppCompat_Body1
        }
    }
}