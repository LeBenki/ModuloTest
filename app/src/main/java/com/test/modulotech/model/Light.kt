package com.test.modulotech.model

class Light(id: Long, deviceName: String, productType: String,
            var intensity: Int, var mode: String) : Device(id, deviceName, productType)
