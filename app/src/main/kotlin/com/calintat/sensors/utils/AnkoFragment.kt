package com.calintat.sensors.utils

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoFragment : Fragment(), AnkoComponent<ViewGroup> {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?): View {

        return createView(AnkoContext.create(activity, container))
    }
}