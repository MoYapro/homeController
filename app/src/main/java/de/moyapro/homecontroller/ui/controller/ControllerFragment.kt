package de.moyapro.homecontroller.ui.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
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
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse


class ControllerFragment : RunningFragment() {

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
        GlobalScope.launch { startBackgroundTvInfoUpdate(viewModel) }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.requireActivity().findViewById<ImageButton>(R.id.button_right).setOnClickListener(HoldableButtonListener(ControllerActivity::right))
        val yourButton = this.requireActivity().findViewById<ImageButton>(R.id.button_left)
        yourButton
            .setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> Log.i(this.javaClass.simpleName, "action clicked")
                    }

                    return v?.onTouchEvent(event) ?: true
                }
            })
        Log.i(this.javaClass.simpleName, "create click listener")
        super.onViewCreated(view, savedInstanceState)
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
