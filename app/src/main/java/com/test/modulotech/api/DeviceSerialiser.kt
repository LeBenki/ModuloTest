package com.test.modulotech.api

import com.google.gson.*
import com.test.modulotech.model.Device
import com.test.modulotech.model.Heater
import com.test.modulotech.model.Light
import com.test.modulotech.model.RollerShutter
import java.lang.reflect.Type


fun getGson(): Gson {
    return GsonBuilder().registerTypeAdapter(Device::class.java, DeviceDeserializer())
        .registerTypeAdapter(Device::class.java, DeviceSerializer()).create()
}

class DeviceDeserializer : JsonDeserializer<Device?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Device? {
        val obj = json.asJsonObject
        val type = obj["productType"].asString

        val id = obj["id"].asLong
        val deviceName = obj["deviceName"].asString
        val productType = obj["productType"].asString

        when {
            type.equals("Light", ignoreCase = true) -> {
                val mode = obj["mode"].asString
                val intensity = obj["intensity"].asInt

                return Light(id, deviceName, productType, intensity, mode)
            }
            type.equals("RollerShutter", ignoreCase = true) -> {
                val position = obj["position"].asInt

                return RollerShutter(id, deviceName, productType, position)
            }
            type.equals("Heater", ignoreCase = true) -> {
                val mode = obj["mode"].asString
                val temperature = obj["temperature"].asDouble

                return Heater(id, deviceName, productType, temperature, mode)
            }
        }
        return null
    }
}

class DeviceSerializer : JsonSerializer<Device?> {
    override fun serialize(
        src: Device?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val obj = JsonObject()
        obj.addProperty("id", src?.id)
        obj.addProperty("deviceName", src?.deviceName)
        obj.addProperty("productType", src?.productType)

        when (src) {
            is Light -> {
                obj.addProperty("mode", src.mode)
                obj.addProperty("intensity", src.intensity)
            }
            is Heater -> {
                obj.addProperty("mode", src.mode)
                obj.addProperty("temperature", src.temperature)
            }
            is RollerShutter -> {
                obj.addProperty("position", src.position)
            }
        }
        return obj
    }
}