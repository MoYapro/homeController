package de.moyapro.homecontroller.ui.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import de.moyapro.homecontroller.MainActivity
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import de.moyapro.homecontroller.communication.tv.request
import de.moyapro.homecontroller.databinding.ControllerFragmentBinding
import de.moyapro.homecontroller.ui.controller.databinding.ControllerViewModel
import de.moyapro.homecontroller.ui.general.RunningFragment
import de.moyapro.homecontroller.ui.util.HoldableButtonListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class ControllerFragment : RunningFragment() {

    companion object {
        fun newInstance() = ControllerFragment()
    }

    private lateinit var viewModel: ControllerViewModel


    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val binding: ControllerFragmentBinding =
            DataBindingUtil.setContentView(this.requireActivity(), R.layout.controller_fragment)
        viewModel = ViewModelProvider(this).get(ControllerViewModel::class.java)
        binding.buttonUp.setOnTouchListener(HoldableButtonListener())
        binding.buttonRight.setOnTouchListener(HoldableButtonListener())
        binding.buttonDown.setOnTouchListener(HoldableButtonListener())
        binding.buttonLeft.setOnTouchListener(HoldableButtonListener())
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
        GlobalScope.launch { startBackgroundTvInfoUpdate(viewModel) }
    }

    private suspend fun startBackgroundTvInfoUpdate(viewModel: ControllerViewModel) {
        while (this.isAdded) {
            if (isRunning) {
                request(
                    TVCommand(
                        TvStatusEnum.VOLUME_STATUS,
                        PreferenceManager.getDefaultSharedPreferences(this.requireActivity())
                    )
                ) { tvResponseString: String ->
                    this.updateStatusModel(
                        tvResponseString,
                        viewModel
                    )
                }

                request(
                    TVCommand(
                        TvStatusEnum.POWER_STATUS,
                        PreferenceManager.getDefaultSharedPreferences(this.requireActivity())
                    )
                ) { tvResponseString: String -> this.handleTvPowerState(tvResponseString) }

            }
            delay(1500)
        }
    }

    private fun updateStatusModel(
        tvResponseString: String,
        viewModel: ControllerViewModel
    ) {
        if (tvResponseString.contains("error")) {
            Log.e(
                this.javaClass.simpleName,
                "Request for volumeStatus not successfull: $tvResponseString"
            )
        } else {
            val volume =
                Json.decodeFromString<VolumeInformationResponse>(tvResponseString).getVolume()
            viewModel.updateVolume(volume.toString())
        }
    }

    private fun handleTvPowerState(
        tvResponseString: String
    ) {
        val hasPower = Json.decodeFromString<PowerStatusResponse>(tvResponseString).hasPower()
        if (!hasPower) {
            this.requireActivity().finish()
            startActivity(Intent(this.activity, MainActivity::class.java))
        }
    }
}
