package com.test.modulotech.ui.devices.piloting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.test.modulotech.R
import com.test.modulotech.api.getGson
import com.test.modulotech.databinding.PilotingHeaterFragmentBinding
import com.test.modulotech.model.Heater
import kotlinx.android.synthetic.main.piloting_heater_fragment.*
import kotlinx.android.synthetic.main.piloting_heater_fragment.finish
import kotlinx.android.synthetic.main.piloting_heater_fragment.modeText

class HeaterPilotingFragment: Fragment() {

    private val viewModel by activityViewModels<DevicePilotingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<PilotingHeaterFragmentBinding>(
            inflater,
            R.layout.piloting_heater_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

    fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        temperatureText.text = (progress.toDouble() / 2).toString()
    }

    fun onCheckedChanged(boolean: Boolean) {
        modeText.text = if (boolean) "ON" else "OFF"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finish.setOnClickListener {
            val device = viewModel.getDevice().value as Heater
            device.temperature = temperatureText.text.toString().toDouble()
            device.mode = modeText.text.toString()
            val intent = Intent()

            val gson = getGson()
            intent.putExtra(DevicePilotingActivity.PARAM_DEVICE, gson.toJson(device))
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }
}