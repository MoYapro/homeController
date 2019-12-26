package de.moyapro.homecontroller.ui.main.databinding

import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.communication.tv.TVCommand
import de.moyapro.homecontroller.communication.tv.TVCommandEnum
import de.moyapro.homecontroller.communication.tv.request

class MainViewModel : ViewModel() {
    private val _volume = MutableLiveData(false)
    val volume: LiveData<Boolean> = _volume

    fun updateVolume(hasPower: Boolean) {
        Log.d(this.javaClass.simpleName, "Set power state to new value: $hasPower")
        _volume.value = hasPower
    }
}