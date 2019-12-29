package de.moyapro.homecontroller.ui.main.databinding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _volume = MutableLiveData(false)
    val volume: LiveData<Boolean> = _volume

    fun updateVolume(hasPower: Boolean) {
        Log.d(this.javaClass.simpleName, "Set power state to new value: $hasPower")
        _volume.value = hasPower
    }
}