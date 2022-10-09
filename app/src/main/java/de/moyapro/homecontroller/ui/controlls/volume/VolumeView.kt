package de.moyapro.homecontroller.ui.controlls.volume

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.homecontroller.tv.Volume
import de.moyapro.homecontroller.ui.VolumeDownButton
import de.moyapro.homecontroller.ui.VolumeUpButton

@Composable
fun VolumeView(
    modifier: Modifier,
    volumePresentationModel: VolumePresentationModel,
    setVolumeAction: (newVolume: Volume) -> Unit,
    setTargetVolumeAction: (newVolume: Volume) -> Unit,
    volumeUpAction: () -> Unit,
    volumeDownAction: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            VolumeDownButton(volumeDownAction, volumePresentationModel.downDisabled)
            VolumeUpButton(volumeUpAction, volumePresentationModel.upDisabled)
        }
        Slider(value = volumePresentationModel.targetVolume.value.toFloat(),
            onValueChange = { setTargetVolumeAction(Volume(it)) },
            onValueChangeFinished = { setVolumeAction(volumePresentationModel.targetVolume) },
            valueRange = VolumeConstants.MIN_VOLUME..VolumeConstants.MAX_VOLUME
        )
    }
}
