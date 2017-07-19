package com.calintat.sensors.utils

import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.widget.TextView
import org.jetbrains.anko.internals.AnkoInternals

object AnkoProperties {

    var Toolbar.titleTextColorResource: Int

        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()

        set(@ColorRes value) { setTitleTextColor(ContextCompat.getColor(context, value)) }

    var Toolbar.overflowIconResource: Int

        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()

        set(@DrawableRes value) { overflowIcon = AppCompatResources.getDrawable(context, value) }

    @Suppress("DEPRECATION") var TextView.textAppearance: Int

        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = AnkoInternals.noGetter()

        set(@StyleRes value) { if (Build.VERSION.SDK_INT >= 23) setTextAppearance(value) else setTextAppearance(context, value) }
}