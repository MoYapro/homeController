package de.moyapro.homecontroller.ui.controlls.volume

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.tv.Volume
import de.moyapro.homecontroller.ui.VolumeDownButton
import de.moyapro.homecontroller.ui.VolumeMuteButton
import de.moyapro.homecontroller.ui.VolumeUpButton

@Composable
fun VolumeView(
    modifier: Modifier,
    volumePresentationModel: VolumePresentationModel,
    applyTargetVolumeToTv: () -> Unit,
    setTargetVolumeAction: (newVolume: Volume) -> Unit,
    volumeUpAction: () -> Unit,
    volumeDownAction: () -> Unit,
    muteAction: () -> Unit,
    restoreAction: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            VolumeMuteButton(volumePresentationModel, muteAction, restoreAction)
            VolumeDownButton(volumeDownAction, volumePresentationModel.downDisabled)
            VolumeUpButton(volumePresentationModel, volumeUpAction, applyTargetVolumeToTv)
        }
        VolumeChangeText(volumePresentationModel.volumeChangeText)
        Slider(value = volumePresentationModel.targetVolume.value.toFloat(),
            onValueChange = { setTargetVolumeAction(Volume(it)) },
            onValueChangeFinished = applyTargetVolumeToTv,
            valueRange = VolumeConstants.MIN_VOLUME..VolumeConstants.MAX_VOLUME
        )
    }
}

@Composable
private fun VolumeChangeText(volumeChangeText: String?) {
    if (null != volumeChangeText) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Icon(Icons.Outlined.VolumeUp, contentDescription = "Volume")
            Text(modifier = Modifier.width(70.dp), text = volumeChangeText)
        }
    } else {
        Spacer(modifier = Modifier
            .alpha(0F)
            .height(24.dp)
        )
    }
}
