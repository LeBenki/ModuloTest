package com.test.modulotech.ui.devices

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.modulotech.R
import com.test.modulotech.model.Device
import kotlinx.android.synthetic.main.cell_device.view.*
import kotlin.random.Random

class DevicesAdapter(private val devices: List<Device>, private val listener: DeviceClickListener) :
    RecyclerView.Adapter<DevicesAdapter.MyViewHolder>() {

    interface DeviceClickListener {
        fun onDeviceClicked(device: Device)
        fun onDeviceDeleteClicked(device: Device)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_device, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(devices[position], listener)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (device: Device, listener: DeviceClickListener) {
            itemView.item_list_device_name.text = device.deviceName
            itemView.item_list_device_type.text = device.productType
            itemView.item_list_device_logo.setColorFilter(
                Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
            itemView.setOnClickListener { listener.onDeviceClicked(device) }
            itemView.item_list_device_delete.setOnClickListener { listener.onDeviceDeleteClicked(device) }
        }
    }
}