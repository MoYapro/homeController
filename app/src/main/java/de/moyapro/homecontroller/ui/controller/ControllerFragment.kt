package de.moyapro.homecontroller.ui.controller

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import de.moyapro.homecontroller.databinding.ControllerFragmentBinding
import de.moyapro.homecontroller.ui.controller.databinding.ControllerViewModel
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse


class ControllerFragment(val viewModel: ControllerViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(this.javaClass.simpleName, "create fragment controller")
        return inflater.inflate(R.layout.controller_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val binding: ControllerFragmentBinding? =
            DataBindingUtil.bind(this.requireView())
        binding!!.viewModel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
    }

    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this.requireActivity());
        Log.d(this.javaClass.simpleName, preferences.getString("prefIP", "192.168.1.111"))
        Log.d(this.javaClass.simpleName, preferences.getString("prefPassword", "invalid"))

    }
}
