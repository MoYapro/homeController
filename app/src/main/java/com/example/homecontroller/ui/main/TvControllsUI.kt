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
import com.example.homecontroller.de.moyapro.homeController.IRCC_CODE
import com.example.homecontroller.de.moyapro.homeController.TVCommand
import com.example.homecontroller.de.moyapro.homeController.TVCommandEnum
import com.example.homecontroller.de.moyapro.homeController.request
import kotlin.reflect.KFunction1


class TvControllsUI : Fragment() {

    companion object {
        fun newInstance() = TvControllsUI()
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
        layout.addView(buildUpButton())
        layout.addView(buildDownButton())
        layout.addView(buildLeftButton())
        layout.addView(buildRightButton())
        layout.addView(buildCenterButton())
        layout.addView(buildIncreaseVolumeButton())
        layout.addView(buildDecreaseVolumeButton())
        layout.addView(buildPowerOffButton())
        layout.addView(buildPowerOnButton())
        layout.addView(buildHdmi4Button())
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

    private fun buildHdmi4Button(): Button {
        return buildDefaultButton("HDMI4", this::switchToHdmi4)
    }

    private fun buildCenterButton(): Button {
        return buildDefaultButton("◍", this::center)
    }

    private fun buildDownButton(): Button {
        return buildDefaultButton("⇩", this::down)
    }

    private fun buildLeftButton(): Button {
        return buildDefaultButton("⇦", this::left)
    }

    private fun buildUpButton(): Button {
        return buildDefaultButton("⇧", this::up)
    }

    private fun buildRightButton(): Button {
        return buildDefaultButton("⇨", this::right)
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

    private fun switchToHdmi4(v: View) {
        switchToHdmi(4)
    }

    private fun changeVolumeRelative(change: String) {
        request(TVCommand(TVCommandEnum.VOLUME, change))
    }

    private fun switchToHdmi(port: Int) {
        request(TVCommand(TVCommandEnum.HDMI, port.toString()))
    }


    private fun up(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.UP.code))
    }

    private fun left(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.LEFT.code))
    }

    private fun right(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.RIGHT.code))
    }

    private fun down(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.DOWN.code))
    }

    private fun center(v: View) {
        request(TVCommand(TVCommandEnum.IRCC, IRCC_CODE.CENTER.code))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
