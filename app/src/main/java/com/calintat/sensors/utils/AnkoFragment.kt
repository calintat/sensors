package com.calintat.sensors.utils

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoFragment<T> : Fragment() {

    abstract val me: T

    abstract val ui: AnkoComponent<T>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?) = ui.createView(AnkoContext.create(activity, me))
}