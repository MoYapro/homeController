package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.Volume

fun buildVolumeUpAction(
    volumeState: VolumeState,
    setVolumeState: (VolumeState) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    setVolumeState(volumeState.copy(targetVolume = volumeState.targetVolume + Volume(1)))
    tvActions.volumeUp()
}

fun buildVolumeDownAction(
    volumeState: VolumeState,
    setVolumeState: (VolumeState) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    setVolumeState(volumeState.copy(targetVolume = volumeState.targetVolume - Volume(1)))
    tvActions.volumeDown()
}

fun buildSetVolumeAction(
    volumeState: VolumeState,
    setVolumeState: (VolumeState) -> Unit,
    tvActions: TvActions,
): (Volume) -> Unit =
    { newVolume ->
        setVolumeState(volumeState.copy(targetVolume = newVolume))
        tvActions.setVolume(newVolume)
    }

fun buildSetTargetVolumeAction(
    volumeState: VolumeState,
    setVolumeState: (VolumeState) -> Unit,
): (Volume) -> Unit = { newVolume ->
    setVolumeState(volumeState.copy(targetVolume = newVolume))
}
