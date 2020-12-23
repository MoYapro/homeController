package de.moyapro.homecontroller.ui.controller.databinding

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.request

@Suppress("UNUSED_PARAMETER")
class ControllerViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(this.getApplication())

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
        request(
            TVCommand(
                TVCommandEnum.VOLUME,
                volumeBar.progress.toString(),
                preferences
            )
        )
    }


}
