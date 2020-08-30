package com.test.modulotech.ui.devices

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.modulotech.R
import com.test.modulotech.api.getGson
import com.test.modulotech.model.Device
import com.test.modulotech.ui.MainActivity.Companion.REQUEST_PILOTING
import com.test.modulotech.ui.devices.piloting.DevicePilotingActivity
import com.test.modulotech.ui.devices.piloting.DevicePilotingActivity.Companion.PARAM_DEVICE
import com.test.modulotech.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_devices.*

class DevicesFragment : Fragment(), DevicesAdapter.DeviceClickListener {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_devices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDevices().observe(viewLifecycleOwner,
            { devices: List<Device> -> initRecyclerView(devices) })
    }

    private fun initRecyclerView(devices: List<Device>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = DevicesAdapter(devices.sortedBy { it.id  }, this@DevicesFragment)
        }
    }

    override fun onDeviceClicked(device: Device) {
        val gson = getGson()

        val intent = Intent(context, DevicePilotingActivity::class.java)
        intent.putExtra(PARAM_DEVICE, gson.toJson(device))
        activity?.startActivityForResult(intent, REQUEST_PILOTING)
    }

    override fun onDeviceDeleteClicked(device: Device) {
        viewModel.removeDevice(device)
    }
}