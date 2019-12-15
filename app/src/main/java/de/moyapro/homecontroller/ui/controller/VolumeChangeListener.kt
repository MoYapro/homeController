package de.moyapro.homecontroller.ui.controller

import android.util.Log
import android.widget.SeekBar
import de.moyapro.homecontroller.TVCommand
import de.moyapro.homecontroller.TVCommandEnum
import de.moyapro.homecontroller.request


class VolumeChangeListener : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i(this.javaClass.simpleName, "hover volume $progress")
        // update model to display currently hovering value: model.text = "Lautstaerke $progress"
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (null == seekBar) {
            return
        }
        val volumeBar: SeekBar = seekBar

        Log.i(this.javaClass.simpleName, "change volume to ${volumeBar.progress}")
        request(TVCommand(TVCommandEnum.VOLUME, volumeBar.progress.toString()))
    }

}