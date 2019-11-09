package com.example.homecontroller.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.homecontroller.de.moyapro.homeController.TVCommand
import com.example.homecontroller.de.moyapro.homeController.TVCommandEnum
import com.example.homecontroller.de.moyapro.homeController.request
import kotlin.reflect.KFunction1


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return buildLayout().rootView
    }

    private fun buildLayout(): View {
        val layout = LinearLayout(this.context)
        layout.addView(buildIncreaseVolumeButton())
        layout.addView(buildDecreaseVolumeButton())
        layout.addView(buildPowerOffButton())
        layout.addView(buildPowerOnButton())
        return layout
    }

    private fun buildIncreaseVolumeButton(): Button {
        return buildDefaultButton("+", this::increaseVolume)
    }

    private fun buildDecreaseVolumeButton(): Button {
        return buildDefaultButton("-", this::decreaseVolume)
    }

    private fun buildPowerOffButton(): Button {
        return buildDefaultButton("AUS", this::powerOff)
    }

    private fun buildPowerOnButton(): Button {
        return buildDefaultButton("AN", this::powerOn)
    }

    private fun buildDefaultButton(
        label: String, action: KFunction1<@ParameterName(
            name = "v"
        ) View, Unit>
    ): Button {
        val button = Button(this.context)
        button.text = label
        button.setOnClickListener(action)
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        lp.addRule(RelativeLayout.CENTER_IN_PARENT)

        // Setting the parameters on the TextView
        button.layoutParams = lp
        return button
    }

    private fun increaseVolume(v: View) {
        changeVolumeRelative("+1")
        Toast.makeText(this.context, "You increased volume.", Toast.LENGTH_SHORT).show()
    }

    private fun decreaseVolume(v: View) {
        changeVolumeRelative("-1")
        Toast.makeText(this.context, "You decrease volume.", Toast.LENGTH_SHORT).show()
    }

    private fun powerOff(v: View) {
        request(TVCommand(TVCommandEnum.POWER, "false"))
    }

    private fun powerOn(v: View) {
        request(TVCommand(TVCommandEnum.POWER, "true"))
    }

    private fun changeVolumeRelative(change: String) {
        request(TVCommand(TVCommandEnum.VOLUME, change))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
