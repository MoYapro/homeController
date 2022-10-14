package de.moyapro.homecontroller.ui.controlls.volume

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.tv.Volume


@Composable
fun VolumeControls(
    modifier: Modifier,
    tvVolume: Volume,
    tvActions: TvActions,
) {
    var volumeState by remember { mutableStateOf(defaultVolumeState) }
    val setVolumeState: (Volume) -> Unit = { newValue: Volume ->
        volumeState = volumeState.copy(targetVolume = newValue, restoreVolume = null)
    }
    val setRestoreVolume: (Volume?) -> Unit = { restoreValue: Volume? ->
        volumeState = volumeState.copy(restoreVolume = restoreValue)
    }
    val volumePresentationModel: VolumePresentationModel =
        VolumePresenter.present(tvVolume, volumeState)
    VolumeView(
        modifier = modifier,
        volumePresentationModel = volumePresentationModel,
        applyTargetVolumeToTv = buildApplyTargetVolumeToTvAction(volumeState, tvActions),
        setTargetVolumeAction = buildSetTargetVolumeAction(setVolumeState),
        volumeUpAction = buildVolumeUpAction(volumeState, setVolumeState, tvActions),
        volumeDownAction = buildVolumeDownAction(volumeState, setVolumeState, tvActions),
        muteAction = buildMuteAction(volumeState, setVolumeState, setRestoreVolume, tvActions),
        restoreAction = buildRestoreAction(volumeState, setVolumeState, setRestoreVolume, tvActions)
    )
}


val defaultVolumeState =
    VolumeState(
        targetVolume = Volume(0),
        restoreVolume = null
    )

