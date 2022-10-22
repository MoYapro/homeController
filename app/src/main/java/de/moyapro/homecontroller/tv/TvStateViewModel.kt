package de.moyapro.homecontroller.tv

import androidx.lifecycle.ViewModel
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.communication.tv.model.PowerStatusEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

class TvStateViewModel : ViewModel() {

    private val _tvState: MutableStateFlow<TvState> =
        MutableStateFlow(
            TvState(
                powerStatus = PowerStatusEnum.STANDBY,
                volume = Volume(1),
                hdmiStatus = emptyList()
            )
        )
    val tvState: StateFlow<TvState> = _tvState

    fun setPowerStatus(newValue: PowerStatusEnum) {
        _tvState.value = tvState.value.copy(powerStatus = newValue)
    }

    fun setVolume(newVolume: Volume) {
        _tvState.value = tvState.value.copy(volume = newVolume)
    }

    fun setHdmiStatus(newHdmiStatus: List<HdmiStatus>){
        _tvState.value = tvState.value.copy(hdmiStatus = newHdmiStatus)
    }
}

data class TvState(
    val powerStatus: PowerStatusEnum,
    val volume: Volume,
    val hdmiStatus: List<HdmiStatus>,
)

@JvmInline
value class Volume(val value: Int) {
    operator fun plus(volume: Volume) = Volume(value + volume.value)

    operator fun minus(volume: Volume) = Volume(value - volume.value)
    operator fun compareTo(volume: Volume): Int = value.compareTo(volume.value)
    operator fun rangeTo(upperRangeEnd: Volume): ClosedFloatingPointRange<Float> {
        require(upperRangeEnd.value >= value) { "Cannot create volume range from $value to $upperRangeEnd" }
        return this.value.toFloat()..upperRangeEnd.value.toFloat()
    }

    constructor(floatValue: Float) : this(floatValue.roundToInt())

}
