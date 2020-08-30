package com.test.modulotech.model

class Heater(id: Long, deviceName: String, productType: String,
             var temperature: Double, var mode: String) : Device(id, deviceName, productType)