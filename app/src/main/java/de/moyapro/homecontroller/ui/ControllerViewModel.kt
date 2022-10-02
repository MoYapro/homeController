package de.moyapro.homecontroller.ui

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.communication.tv.model.PowerStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ControllerViewModel(
    var volume: String = "10",
    private val _powerStatus: MutableStateFlow<PowerStatus> = MutableStateFlow(PowerStatus("active")),
    val powerStatus: StateFlow<PowerStatus> = _powerStatus,
) : ViewModel() {
    fun hasPower(): Boolean {
        return "active" == powerStatus.value.status
    }

    fun setPowerStatus(newValue: String) {
        _powerStatus.value = PowerStatus(newValue)
    }
}

