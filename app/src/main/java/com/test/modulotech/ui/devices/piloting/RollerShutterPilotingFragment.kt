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
import com.test.modulotech.databinding.PilotingRollershutterFragmentBinding
import com.test.modulotech.model.RollerShutter
import kotlinx.android.synthetic.main.piloting_rollershutter_fragment.*

class RollerShutterPilotingFragment: Fragment() {

    private val viewModel by activityViewModels<DevicePilotingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<PilotingRollershutterFragmentBinding>(
            inflater,
            R.layout.piloting_rollershutter_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

    fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        positionText.text = progress.toString()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finish.setOnClickListener {
            val device = viewModel.getDevice().value as RollerShutter
            device.position = positionText.text.toString().toInt()
            val intent = Intent()

            val gson = getGson()
            intent.putExtra(DevicePilotingActivity.PARAM_DEVICE, gson.toJson(device))
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }
}