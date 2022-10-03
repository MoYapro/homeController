package de.moyapro.homecontroller.tv

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.communication.tv.model.PowerStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TvState : ViewModel() {
    var hdmiChannel: Int? = null
    var volume: String = "10"
    private val _powerStatus: MutableStateFlow<PowerStatus> =
        MutableStateFlow(PowerStatus("active"))
    val powerStatus: StateFlow<PowerStatus> = _powerStatus

    fun setPowerStatus(newValue: String) {
        _powerStatus.value = PowerStatus(newValue)
    }
}
