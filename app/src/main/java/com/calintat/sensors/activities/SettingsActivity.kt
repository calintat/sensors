package com.calintat.sensors.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.calintat.sensors.R
import com.calintat.sensors.ui.SettingsUI
import org.jetbrains.anko.setContentView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        SettingsUI.setContentView(this)

        fragmentManager.beginTransaction().replace(R.id.container, SettingsFragment()).commit()
    }
}