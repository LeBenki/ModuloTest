package com.test.modulotech.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.modulotech.api.ModuloTechApi
import com.test.modulotech.model.Device
import com.test.modulotech.model.User
import com.test.modulotech.repository.ModuloTechRepository
import io.reactivex.rxkotlin.subscribeBy

class MainViewModel : ViewModel() {

    private val repository = ModuloTechRepository(ModuloTechApi.invoke())
    private val networkCallSuccess = MutableLiveData<Boolean>()

    //Device Fragment
    private val devices = MutableLiveData<List<Device>>()

    //User Fragment
    private val user = MutableLiveData<User>()
    private val isEditMode = MutableLiveData(false)

    fun executeNetworkCall() =
        repository.getAllData
            .subscribeBy(
                onSuccess = { apiResponse ->
                    devices.postValue(apiResponse.devices)
                    user.postValue(apiResponse.user)
                    networkCallSuccess.postValue(true)
                },
                onError = {
                    networkCallSuccess.postValue(false)
                })

    fun getNetworkCallSuccess(): LiveData<Boolean> = networkCallSuccess

    fun getDevices(): LiveData<List<Device>> = devices

    fun getUser(): LiveData<User> = user

    fun getIsEditMode(): LiveData<Boolean> = isEditMode

    fun removeDevice(device: Device) {
        val list = devices.value?.toMutableList()
        list?.remove(device)
        devices.postValue(list)
    }

    fun editDevice(device: Device) {
        val list = devices.value?.toMutableList()
        val old = list?.find { it.id == device.id }
        list?.remove(old)
        list?.add(device)
        devices.postValue(list)
    }

    fun editUser(userModel: User) {
        user.postValue(userModel)
    }

    fun changeEditMode(editMode: Boolean) {
        isEditMode.postValue(editMode)
    }
}