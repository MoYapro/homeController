package de.moyapro.homecontroller.ui.main

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.request
import de.moyapro.homecontroller.databinding.MainFragmentBinding
import de.moyapro.homecontroller.ui.controller.ControllerActivity
import de.moyapro.homecontroller.ui.general.RunningFragment
import de.moyapro.homecontroller.ui.main.databinding.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MainFragment : RunningFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val binding: MainFragmentBinding =
            DataBindingUtil.setContentView(this.requireActivity(), R.layout.main_fragment)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
        GlobalScope.launch { startBackgroundTvInfoUpdate(viewModel) }
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        viewModel.updateSsid(wifiManager.connectionInfo.ssid)
    }

    private suspend fun startBackgroundTvInfoUpdate(viewModel: MainViewModel) {
        while (this.isAdded) {
            if (isRunning) {
                request(
                    TVCommand(
                        TvStatusEnum.POWER_STATUS,
                        PreferenceManager.getDefaultSharedPreferences(this.requireActivity())
                    )
                ) { tvResponseString: String ->
                    this.updateStatusModel(
                        tvResponseString,
                        viewModel
                    )
                }
            }
            delay(1500)
        }
    }

    private fun updateStatusModel(
        tvResponseString: String,
        viewModel: MainViewModel
    ) {
        val hasPower = Json.decodeFromString<PowerStatusResponse>(tvResponseString).hasPower()
        viewModel.updatePowerState(hasPower)
        if (hasPower) {
            this.requireActivity().finish()
            startActivity(Intent(this.activity, ControllerActivity::class.java))
        }
    }
}
