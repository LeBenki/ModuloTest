package com.test.modulotech.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.modulotech.R
import com.test.modulotech.api.getGson
import com.test.modulotech.model.Device
import com.test.modulotech.ui.devices.piloting.DevicePilotingActivity


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_devices, R.id.navigation_user
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.executeNetworkCall()

        viewModel.getNetworkCallSuccess().observe(this,
            { success -> if (!success) Toast.makeText(this, getString(R.string.error),
                Toast.LENGTH_LONG).show()})
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PILOTING && resultCode == RESULT_OK) {
            val gson = getGson()

            val device = gson.fromJson(data?.extras?.getString(DevicePilotingActivity.PARAM_DEVICE), Device::class.java)
            viewModel.editDevice(device)
        }
    }

    companion object {
        const val REQUEST_PILOTING = 4567
    }
}