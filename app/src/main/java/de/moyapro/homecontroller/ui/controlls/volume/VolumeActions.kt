package de.moyapro.homecontroller.ui.controlls.volume

import android.util.Log
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.Volume

const val TAG = "volume action"

fun buildVolumeUpAction(
    volumeState: VolumeState,
    setVolumeState: (Volume) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    setVolumeState(volumeState.targetVolume + Volume(1))
    tvActions.volumeUp()
}

fun buildVolumeDownAction(
    volumeState: VolumeState,
    setVolumeState: (Volume) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    setVolumeState(volumeState.targetVolume - Volume(1))
    tvActions.volumeDown()
}

fun buildSetVolumeAction(
    setVolumeState: (Volume) -> Unit,
    tvActions: TvActions,
): (Volume) -> Unit =
    { newVolume ->
        setVolumeState(newVolume)
        tvActions.setVolume(newVolume)
    }

fun buildSetTargetVolumeAction(
    setVolumeState: (Volume) -> Unit,
): (Volume) -> Unit = { newVolume ->
    setVolumeState(newVolume)
}

fun buildRestoreAction(
    volumeState: VolumeState,
    setVolumeState: (Volume) -> Unit,
    setRestoreVolume: (Volume?) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    val volume = volumeState.restoreVolume ?: Volume(0)
    setRestoreVolume(null)
    setVolumeState(volume)
    tvActions.setVolume(volume)
}

fun buildMuteAction(
    volumeState: VolumeState,
    setVolumeState: (Volume) -> Unit,
    setRestoreVolume: (Volume) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    val newVolume = Volume(0)
    val restoreVolume = volumeState.targetVolume
    setVolumeState(newVolume)
    setRestoreVolume(restoreVolume)
    tvActions.setVolume(newVolume)
}
