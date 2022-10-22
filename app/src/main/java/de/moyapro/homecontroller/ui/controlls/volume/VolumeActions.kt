package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.Volume

const val TAG = "volume action"

fun buildVolumeUpAction(
    volumeState: VolumeState,
    setVolumeState: (Volume?) -> Unit,
): () -> Unit = {
    setVolumeState(volumeState.targetVolume?.plus(Volume(1)) ?: null)
}

fun buildVolumeDownAction(
    volumeState: VolumeState,
    setVolumeState: (Volume?) -> Unit,
): () -> Unit = {
    setVolumeState(volumeState.targetVolume?.minus(Volume(1)) ?: null)
}

fun buildApplyTargetVolumeToTvAction(
    volumeState: VolumeState,
    tvActions: TvActions,
): () -> Unit = { tvActions.setVolume(volumeState.targetVolume ?: Volume(0)) }

fun buildSetTargetVolumeAction(
    setVolumeState: (Volume) -> Unit,
): (Volume) -> Unit = { newVolume -> setVolumeState(newVolume) }

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
    setVolumeState: (Volume?) -> Unit,
    setRestoreVolume: (Volume?) -> Unit,
    tvActions: TvActions,
): () -> Unit = {
    val newVolume = Volume(0)
    val restoreVolume = volumeState.targetVolume
    setVolumeState(newVolume)
    setRestoreVolume(restoreVolume)
    tvActions.setVolume(newVolume)
}
