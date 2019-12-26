package de.moyapro.homecontroller.ui.databinding

import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.TVCommand
import de.moyapro.homecontroller.TVCommandEnum
import de.moyapro.homecontroller.request

class ControllerViewModel : ViewModel() {
    private val _volume = MutableLiveData("0")
    val volume: LiveData<String> = _volume

    fun updateVolume(newVolume: String) {
        Log.d(this.javaClass.simpleName, "Set volume to new value: $newVolume")
        _volume.value = newVolume
    }


    fun onVolumeSelect(seekBar: SeekBar, newVolume: Int, fromUser: Boolean) {
        updateVolume(newVolume.toString())
    }

    fun onStopTrackingTouch(volumeBar: SeekBar) {

        Log.i(this.javaClass.simpleName, "change volume to ${volumeBar.progress}")
        request(TVCommand(TVCommandEnum.VOLUME, volumeBar.progress.toString()))
    }


}