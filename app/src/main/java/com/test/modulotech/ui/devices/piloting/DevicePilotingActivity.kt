package com.test.modulotech.ui.devices.piloting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.modulotech.R
import com.test.modulotech.api.getGson
import com.test.modulotech.model.Device
import com.test.modulotech.model.Heater
import com.test.modulotech.model.RollerShutter

class DevicePilotingActivity : AppCompatActivity() {

    private val viewModel by viewModels<DevicePilotingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.piloting_activity)

        intent.extras.let {
            val gson = getGson()
            val device = gson.fromJson(it?.getString(PARAM_DEVICE), Device::class.java)

            viewModel.setDevice(device)

            viewModel.getDevice().observe(this, {
                launchFragment(it)
            })

        }
    }

    private fun launchFragment(device: Device) {

        title = device.productType

        val fragment = when(device) {
            is Heater -> HeaterPilotingFragment()
            is RollerShutter -> RollerShutterPilotingFragment()
            else -> LightPilotingFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, fragment)
            .commitAllowingStateLoss()

    }

    companion object {
        const val PARAM_DEVICE = "param_device"
    }
}