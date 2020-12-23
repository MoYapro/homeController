package de.moyapro.homecontroller.ui.main.databinding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _hasPower = MutableLiveData(false)
    val hasPower: LiveData<Boolean> = _hasPower

    private val _ssid = MutableLiveData("...")
    val ssid: LiveData<String> = _ssid

    fun updatePowerState(hasPower: Boolean) {
        _hasPower.value = hasPower
    }

    fun updateSsid(ssid: String) {
        _ssid.value = ssid
    }

}
