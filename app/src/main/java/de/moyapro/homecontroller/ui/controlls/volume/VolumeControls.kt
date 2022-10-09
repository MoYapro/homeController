package de.moyapro.homecontroller.ui.controlls.volume

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
    val setVolumeState = { newValue: VolumeState -> volumeState = newValue }
    val volumePresentationModel: VolumePresentationModel =
        VolumePresenter.present(tvVolume, volumeState)
    VolumeView(
        modifier = modifier,
        volumePresentationModel = volumePresentationModel,
        setVolumeAction = buildSetVolumeAction(volumeState, setVolumeState, tvActions),
        setTargetVolumeAction = buildSetTargetVolumeAction(volumeState, setVolumeState),
        volumeUpAction = buildVolumeUpAction(volumeState, setVolumeState, tvActions),
        volumeDownAction = buildVolumeDownAction(volumeState, setVolumeState, tvActions),

    )
}

val defaultVolumeState =
    VolumeState(
        targetVolume = Volume(0),
    )

