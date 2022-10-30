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
    var volumeState by remember { mutableStateOf(VolumeState()) }
    val setVolumeState: (Volume?) -> Unit = { newValue: Volume? ->
        volumeState = when (newValue) {
            null -> volumeState
            Volume(0) -> volumeState.copy(targetVolume = newValue)
            else -> volumeState.copy(targetVolume = newValue, restoreVolume = null)
        }
    }
    val setRestoreVolume: (Volume?) -> Unit = { restoreValue: Volume? ->
        Log.i(TAG, "set restorevolume to $restoreValue")
        volumeState = volumeState.copy(restoreVolume = restoreValue)
    }

    var volumePresentationModel: VolumePresentationModel by remember {
        mutableStateOf(VolumePresentationModel())
    }
    if (tvVolume != volumePresentationModel.tvVolume) {
        setVolumeState(tvVolume)
    }
    volumePresentationModel =
        VolumePresenter.present(tvVolume, volumeState)

    VolumeView(
        modifier = modifier,
        volumePresentationModel = volumePresentationModel,
        applyTargetVolumeToTv = buildApplyTargetVolumeToTvAction(volumeState, tvActions),
        setTargetVolumeAction = buildSetTargetVolumeAction(setVolumeState),
        volumeUpAction = buildVolumeUpAction(volumeState, setVolumeState),
        volumeDownAction = buildVolumeDownAction(volumeState, setVolumeState),
        muteAction = buildMuteAction(volumeState, setVolumeState, setRestoreVolume, tvActions),
        restoreAction = buildRestoreAction(volumeState, setVolumeState, setRestoreVolume, tvActions)
    )
}
