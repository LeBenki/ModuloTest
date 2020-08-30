package com.test.modulotech.ui.devices.piloting

import android.app.Activity.RESULT_OK
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
import com.test.modulotech.databinding.PilotingLightFragmentBinding
import com.test.modulotech.model.Light
import com.test.modulotech.ui.devices.piloting.DevicePilotingActivity.Companion.PARAM_DEVICE
import kotlinx.android.synthetic.main.piloting_light_fragment.*

class LightPilotingFragment: Fragment() {

    private val viewModel by activityViewModels<DevicePilotingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<PilotingLightFragmentBinding>(
            inflater,
            R.layout.piloting_light_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

     fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
         intensityText.text = progress.toString()
     }

    fun onCheckedChanged(boolean: Boolean) {
        modeText.text = if (boolean) "ON" else "OFF"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finish.setOnClickListener {
            val device = viewModel.getDevice().value as Light
            device.intensity = intensityText.text.toString().toInt()
            device.mode = modeText.text.toString()
            val intent = Intent()

            val gson = getGson()
            intent.putExtra(PARAM_DEVICE, gson.toJson(device))
            activity?.setResult(RESULT_OK, intent)
            activity?.finish()
        }
    }
}