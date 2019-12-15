package de.moyapro.homecontroller.ui.controller

import android.util.Log
import android.widget.SeekBar
import de.moyapro.homecontroller.TVCommand
import de.moyapro.homecontroller.TVCommandEnum
import de.moyapro.homecontroller.request
import de.moyapro.homecontroller.ui.databinding.ControllerViewModel


class VolumeChangeListener(var viewModel: ControllerViewModel) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, volumeValue: Int, fromUser: Boolean) {
        Log.d(this.javaClass.simpleName, "hover volume $volumeValue")
        viewModel.updateVolume(volumeValue.toString())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        Log.d(this.javaClass.simpleName, "start changing volume")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (null == seekBar) {
            return
        }
        val volumeBar: SeekBar = seekBar

        Log.i(this.javaClass.simpleName, "change volume to ${volumeBar.progress}")
        request(TVCommand(TVCommandEnum.VOLUME, volumeBar.progress.toString()))
    }

}