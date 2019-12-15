package de.moyapro.homecontroller.ui.databinding

import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ControllerViewModel : ViewModel() {
    private val _volume = MutableLiveData("5")
    val volume: LiveData<String> = _volume

    fun updateVolume(newVolume: String) {
        _volume.value = newVolume
    }


    fun onVolumeSelect(seekBar: SeekBar, newVolume: Int, fromUser: Boolean) {
        updateVolume(newVolume.toString())
    }


}