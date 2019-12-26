package de.moyapro.homecontroller.ui.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.moyapro.homecontroller.*
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.request
import de.moyapro.homecontroller.databinding.ControllerFragmentBinding
import de.moyapro.homecontroller.communication.tv.model.TvResponse
import de.moyapro.homecontroller.ui.controller.databinding.ControllerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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
        GlobalScope.launch {startBackgroundTvInfoUpdate(viewModel)}
    }

    private suspend fun startBackgroundTvInfoUpdate(viewModel: ControllerViewModel) {

        while (this.isAdded) {
            request(
                TVCommand(
                    TvStatusEnum.VOLUME_STATUS
                )
            ) { tvResponseString: String -> this.updateStatusModel(tvResponseString, viewModel) }
            delay(1500)
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun updateStatusModel(
        tvResponseString: String,
        viewModel: ControllerViewModel
    ) {
        val volume = Json.parse<TvResponse>(tvResponseString).getVolume()
        Log.d(this.javaClass.simpleName, "Set volume to new value: $volume")
        viewModel.updateVolume(volume.toString())
    }
}
