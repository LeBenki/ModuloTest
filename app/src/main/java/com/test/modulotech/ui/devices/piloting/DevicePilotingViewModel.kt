package com.test.modulotech.ui.devices.piloting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.modulotech.model.Device

class DevicePilotingViewModel : ViewModel() {

    private val device = MutableLiveData<Device>()

    fun getDevice(): LiveData<Device> = device

    fun setDevice(deviceModel: Device) = device.postValue(deviceModel)
}