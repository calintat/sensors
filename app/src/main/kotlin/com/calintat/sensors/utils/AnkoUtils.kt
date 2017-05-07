package com.calintat.sensors.utils

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.PropertyWithoutGetterException

object AnkoUtils {

    @Suppress("DEPRECATION") var TextView.textAppearance: Int

        get() = throw PropertyWithoutGetterException("textAppearance")

        set(value) {

            if (Build.VERSION.SDK_INT >= 23) {

                setTextAppearance(value)
            }
            else {

                setTextAppearance(context, value)
            }
        }

    abstract class AnkoFragment : Fragment(), AnkoComponent<ViewGroup> {

        final override fun onCreateView(i: LayoutInflater?, c: ViewGroup, s: Bundle?): View {

            return createView(AnkoContext.create(activity, c))
        }
    }
}