package de.moyapro.homecontroller.tv

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TvStateViewModel : ViewModel() {

    private val _tvState: MutableStateFlow<TvState> =
        MutableStateFlow(
            TvState(
                powerStatus = PowerStatusEnum.ON,
                volume = Volume(0),
                selectedHdmiChannel = HdmiChannelId(),
                availableHdmiChannels = listOf(HdmiChannel())
            )
        )
    val tvState: StateFlow<TvState> = _tvState

    fun setPowerStatus(newValue: PowerStatusEnum) {
        _tvState.value = tvState.value.copy(powerStatus = newValue)
    }

    fun setVolume(newVolume: Volume) {
        _tvState.value = tvState.value.copy(volume = newVolume)
    }
}

data class TvState(
    val powerStatus: PowerStatusEnum,
    val volume: Volume,
    val selectedHdmiChannel: HdmiChannelId,
    val availableHdmiChannels: List<HdmiChannel>,
)

class HdmiChannel {

}

class HdmiChannelId {

}

inline class Volume(val value: Int) {
    operator fun plus(volume: Volume) = Volume(value + volume.value)

    operator fun minus(volume: Volume) = Volume(value - volume.value)
}
