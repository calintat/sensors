package com.calintat.sensors.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.IdRes
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.calintat.sensors.R
import com.calintat.sensors.recycler.Adapter
import com.calintat.sensors.utils.CSVUtils
import com.calintat.sensors.utils.GraphPlotter
import com.calintat.sensors.utils.PreferenceUtils
import com.calintat.sensors.utils.SensorManagerUtils
import com.jjoe64.graphview.GraphView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val KEY_DEFAULT = "com.calintat.sensors.KEY_DEFAULT"

    private val KEY_CURRENT = "com.calintat.sensors.KEY_CURRENT"

    private var selection: Int? = null

    private lateinit var sensorFragment: SensorFragment

    private lateinit var loggerFragment: LoggerFragment

    //----------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setTheme()

        setToolbar()

        setActionButton()

        setNavigationView()

        init(savedInstanceState)
    }

    override fun onBackPressed() {

        when {

            drawerLayout.isDrawerOpen(navigationView) -> drawerLayout.closeDrawers()

            else -> super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        selection?.let { outState.putInt(KEY_CURRENT, it) }

        super.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 0) {

            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(coordinatorLayout, "Permission required", Snackbar.LENGTH_LONG)

                        .setAction("Go to settings") { gotoInfoPage() }

                        .show()
            }
            else {

                actionSave()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //----------------------------------------------------------------------------------------------

    fun init(savedInstanceState: Bundle?) {

        val defaultId = SensorManagerUtils.getDefId(this)

        if (defaultId == null) { /* no sensors available */

            val builder = AlertDialog.Builder(this)

            builder.setTitle("No supported sensors found")

            builder.setPositiveButton("Exit") { d, w -> finish() }

            builder.setNegativeButton("Send feedback") { d, w -> gotoFeedback(); finish() }

            builder.setCancelable(false)

            builder.show()
        }
        else if (savedInstanceState == null) { /* opened from launcher or app shortcut */

            select(PreferenceUtils.getInt(this, KEY_DEFAULT, defaultId))
        }
        else { /* orientation change, activity resumed, etc */

            select(savedInstanceState.getInt(KEY_CURRENT, defaultId))
        }
    }

    fun select(@IdRes id: Int) {

        selection = id

        PreferenceUtils.putInt(this, KEY_DEFAULT, id)

        val item = SensorManagerUtils.getItem(id)

        sensorFragment = SensorFragment()

        loggerFragment = LoggerFragment()

        val arguments = Bundle()

        arguments.putInt(SensorFragment.KEY_TYPE, item.type)

        arguments.putInt(SensorFragment.KEY_UNIT, item.unit)

        arguments.putInt(SensorFragment.KEY_SIZE, item.size)

        sensorFragment.arguments = arguments

        replace(R.id.fragment_sensor, sensorFragment)

        replace(R.id.fragment_logger, loggerFragment)

        invalidateMenu()
    }

    //----------------------------------------------------------------------------------------------

    private fun setTheme() {

        when (PreferenceUtils.getBoolean(this, "pref_dark_theme", false)) {

            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setToolbar() {

        toolbar.inflateMenu(R.menu.action)

        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.action_graph -> actionGraph()

                R.id.action_clear -> actionClear()

                R.id.action_save -> actionSave()

                R.id.action_info -> actionInfo()

                else -> false
            }
        }

        toolbar.setNavigationIcon(R.drawable.ic_action_menu)

        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(navigationView) }

        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_overflow)
    }

    private fun setActionButton() {

        actionButton.setOnClickListener { actionAdd() }
    }

    private fun setNavigationView() {

        navigationView.inflateMenu(R.menu.navigation)

        navigationView.setNavigationItemSelectedListener {

            drawerLayout.closeDrawers()

            when (it.itemId) {

                R.id.navigation_settings -> gotoSettings()

                R.id.navigation_feedback -> gotoFeedback()

                else -> select(it.itemId)
            }

            true
        }

        SensorManagerUtils.getIds().forEach {

            navigationView.menu.findItem(it).isVisible = SensorManagerUtils.isSensorAvailable(this, it)
        }
    }

    //----------------------------------------------------------------------------------------------

    private fun actionClear(): Boolean {

        loggerFragment = LoggerFragment()

        fragmentManager.beginTransaction().replace(R.id.fragment_logger, loggerFragment).commit()

        invalidateMenu()

        return true
    }

    private fun actionGraph(): Boolean {

        val dialog = Dialog(this,android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar)

        dialog.setContentView(R.layout.dialog_graph)

        val graphView = dialog.findViewById(R.id.graph_view) as GraphView

        graphView.viewport.isScalable = true

        GraphPlotter.plot(graphView, loggerFragment.getLogEntries())

        dialog.show()

        val button = dialog.findViewById(R.id.graph_button) as Button

        button.setOnClickListener { dialog.cancel() }

        return true
    }

    @SuppressLint("SetTextI18n")
    private fun actionInfo(): Boolean {

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_info)

        val item = SensorManagerUtils.getItem(selection!!)

        val sensorManager = SensorManagerUtils.getManager(this)

        val sensor = sensorManager.getDefaultSensor(item.type)

        val unit = resources.getString(item.unit)

        val name = dialog.findViewById(R.id.sensor_name) as TextView

        val vendor = dialog.findViewById(R.id.sensor_vendor) as TextView

        val version = dialog.findViewById(R.id.sensor_version) as TextView

        val power = dialog.findViewById(R.id.sensor_power) as TextView

        val resolution = dialog.findViewById(R.id.sensor_resolution) as TextView

        val maximumRange = dialog.findViewById(R.id.sensor_maximum_range) as TextView

        name.text = sensor.name

        vendor.text = sensor.vendor

        version.text = sensor.version.toString()

        power.text = "${sensor.power} mA"

        resolution.text = "${sensor.resolution} $unit"

        maximumRange.text = "${sensor.resolution} $unit"

        dialog.show()

        return true
    }

    private fun actionSave(): Boolean {

        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {

            ActivityCompat.requestPermissions(this, arrayOf(permission), 0)

            return false
        }

        CSVUtils.writeToCSV(loggerFragment.startTime, loggerFragment.getLogEntries())

        Toast.makeText(this, "Saved to csv file", Toast.LENGTH_SHORT).show()

        return true
    }

    private fun actionAdd() {

        loggerFragment.log(sensorFragment.entries)

        invalidateMenu()
    }

    //----------------------------------------------------------------------------------------------

    private fun invalidateMenu() {

        toolbar.menu.findItem(R.id.action_graph).isVisible = !loggerFragment.isEmpty

        toolbar.menu.findItem(R.id.action_clear).isVisible = !loggerFragment.isEmpty

        toolbar.menu.findItem(R.id.action_save).isVisible = !loggerFragment.isEmpty
    }

    private fun replace(@IdRes containerViewId: Int, fragment: Fragment) {

        fragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
    }

    //----------------------------------------------------------------------------------------------

    private fun gotoSettings() {

        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun gotoFeedback() {

        val builder = CustomTabsIntent.Builder()

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.red_500))

        builder.build().launchUrl(this, Uri.parse("https://github.com/calintat/Sensors/issues"))
    }

    private fun gotoInfoPage() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        intent.data = Uri.parse("package:com.calintat.sensors")

        startActivity(intent)
    }

    //----------------------------------------------------------------------------------------------

    class SensorFragment : Fragment() {

        var entries = floatArrayOf()

        companion object {

            val KEY_TYPE = "com.calintat.sensors.KEY_TYPE"

            val KEY_SIZE = "com.calintat.sensors.KEY_SIZE"

            val KEY_UNIT = "com.calintat.sensors.KEY_UNIT"
        }

        private var sensorValue1: TextView? = null

        private var sensorValue2: TextView? = null

        private var sensorValue3: TextView? = null

        private val type by lazy { arguments.getInt(KEY_TYPE) }

        private val size by lazy { arguments.getInt(KEY_SIZE) }

        private val unit by lazy { arguments.getInt(KEY_UNIT) }

        private val sensorManager by lazy { activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager }

        private val sensor by lazy { sensorManager.getDefaultSensor(type) }

        private val listener = object : SensorEventListener {

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

                /* do nothing */
            }

            override fun onSensorChanged(event: SensorEvent) {

                val newValues = event.values.copyOfRange(0, size)

                entries = newValues

                sensorValue1?.text = "${newValues[0]}"

                sensorValue2?.text = "${newValues[1]}"

                sensorValue3?.text = "${newValues[2]}"
            }

            fun wow(a: Float, b: Float, e: Float) = Math.abs(a - b) < e
        }

        private val EPSILON = 0.01

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {

            val layoutRes = when (size) {

                1 -> R.layout.fragment_sensor_1d

                3 -> R.layout.fragment_sensor_3d

                else -> 0
            }

            val view = inflater.inflate(layoutRes, container, false)

            sensorValue1 = view.findViewById(R.id.sensor_value1) as? TextView

            sensorValue2 = view.findViewById(R.id.sensor_value2) as? TextView

            sensorValue3 = view.findViewById(R.id.sensor_value3) as? TextView

            val unit1 = view.findViewById(R.id.sensor_unit1) as? TextView

            val unit2 = view.findViewById(R.id.sensor_unit2) as? TextView

            val unit3 = view.findViewById(R.id.sensor_unit3) as? TextView

            unit1?.setText(unit)

            unit2?.setText(unit)

            unit3?.setText(unit)

            return view
        }

        override fun onResume() {

            super.onResume()

            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        override fun onPause() {

            super.onPause()

            sensorManager.unregisterListener(listener)
        }
    }

    class LoggerFragment : Fragment() {

        val adapter by lazy { Adapter(activity) }

        val startTime by lazy { System.currentTimeMillis() }

        var isEmpty = true

        fun log(values: FloatArray) {

            isEmpty = false

            val timeMillis = System.currentTimeMillis() - startTime

            adapter.add(timeMillis.toFloat() / 1000, values)
        }

        fun getLogEntries() = adapter.items

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {

            val view = inflater.inflate(R.layout.fragment_logger, container, false)

            val recyclerView = view.findViewById(R.id.logger_list) as RecyclerView

            recyclerView.layoutManager = LinearLayoutManager(activity)

            recyclerView.adapter = adapter

            return view
        }
    }
}
