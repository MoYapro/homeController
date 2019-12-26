package de.moyapro.homecontroller.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.moyapro.homecontroller.R
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TvStatusEnum
import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.request
import de.moyapro.homecontroller.databinding.MainFragmentBinding
import de.moyapro.homecontroller.ui.main.databinding.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse


class MainFragment : Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // <-- this enables MutableLiveData to be update on your UI
        GlobalScope.launch { startBackgroundTvInfoUpdate(viewModel) }
    }

    private suspend fun startBackgroundTvInfoUpdate(viewModel: MainViewModel) {
        while (this.isAdded) {
            request(
                TVCommand(
                    TvStatusEnum.POWER_STATUS
                )
            ) { tvResponseString: String -> this.updateStatusModel(tvResponseString, viewModel) }
            delay(1500)
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    fun updateStatusModel(
        tvResponseString: String,
        viewModel: MainViewModel
    ) {
        Log.d(this.javaClass.simpleName, "Set volume to new value: $tvResponseString")
        val hasPower = Json.parse<PowerStatusResponse>(tvResponseString).hasPower()
        viewModel.updateVolume(hasPower)
    }
}
