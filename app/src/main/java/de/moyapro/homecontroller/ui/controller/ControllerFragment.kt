package de.moyapro.homecontroller.ui.controller

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.moyapro.homecontroller.MainActivity
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import de.moyapro.homecontroller.databinding.ControllerFragmentBinding
import de.moyapro.homecontroller.ui.controller.databinding.ControllerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse


class ControllerFragment : Fragment() {

    companion object {
        fun newInstance() = ControllerFragment()
    }

    private lateinit var viewModel: ControllerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(this.javaClass.simpleName, "create fragment controller")
        return inflater.inflate(R.layout.controller_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val binding: ControllerFragmentBinding =
            DataBindingUtil.setContentView(this.requireActivity(), R.layout.controller_fragment)
        viewModel = ViewModelProviders.of(this).get(ControllerViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun updateStatusModel(
        tvResponseString: String,
        viewModel: ControllerViewModel
    ) {
        Log.d(this.javaClass.simpleName, "Set volume to new value: $tvResponseString")
        val volume = Json.parse<VolumeInformationResponse>(tvResponseString).getVolume()
        viewModel.updateVolume(volume.toString())
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun handleTvPowerState(
        tvResponseString: String
    ) {
        Log.d(this.javaClass.simpleName, "Set power status to new value: $tvResponseString")
        val hasPower = Json.parse<PowerStatusResponse>(tvResponseString).hasPower()
        if (!hasPower) {
            this.requireActivity().finish()
            startActivity(Intent(this.activity, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this.requireActivity());
        Log.d(this.javaClass.simpleName, preferences.getString("prefIP", "192.168.1.111"))
        Log.d(this.javaClass.simpleName, preferences.getString("prefPassword", "invalid"))

    }
}
