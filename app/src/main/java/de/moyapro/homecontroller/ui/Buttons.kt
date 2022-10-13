package de.moyapro.homecontroller.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.SettingsInputHdmi
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.TvOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.tv.Volume
import de.moyapro.homecontroller.ui.controlls.volume.VolumePresentationModel

@Composable
fun OffButton(offAction: () -> Unit, modifier: Modifier = Modifier) {
    Button(modifier = modifier, onClick = offAction) {
        Icon(Icons.Outlined.TvOff, contentDescription = "off")
    }
}

@Composable
fun OnButton(onAction: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = onAction) {
        Icon(Icons.Outlined.Tv, contentDescription = "on")
    }
}

@Composable
fun SettingsButton(modifier: Modifier, openSettings: () -> Unit) {
    Button(modifier = modifier,
        onClick = openSettings
    ) {
        Icon(Icons.Filled.Settings, contentDescription = "settings")
    }
}

@Composable
fun MainButton(modifier: Modifier, openMain: () -> Unit) {
    Button(modifier = modifier,
        onClick = openMain
    ) {
        Icon(Icons.Filled.Domain, contentDescription = "main")
    }
}


@Composable
fun BackButton(backAction: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(.5F), onClick = backAction) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Composable
fun HomeButton(homeAction: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = homeAction) {
        Icon(Icons.Filled.Home, contentDescription = "Home")
    }
}


@Composable
fun DownButton(downAction: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), onClick = downAction) {
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
    }
}

@Composable
fun RightButton(rightAction: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth(1F)
        .fillMaxHeight(.5F),
        onClick = rightAction) {
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Right")
    }
}

@Composable
fun OkButton(okAction: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth(.5F)
        .fillMaxHeight(.5F),
        onClick = okAction) {
        Text("OK")
    }
}

@Composable
fun LeftButton(leftAction: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth(.33F)
        .fillMaxHeight(.5F),
        onClick = leftAction) {
        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Left")
    }
}

@Composable
fun UpButton(upAction: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan),
        onClick = upAction) {
        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
    }
}


@Composable
fun HdmiButton(modifier: Modifier, status: HdmiStatus, hdmiAction: (uri: String) -> Unit) {
    Button(modifier = modifier, enabled = true,
        onClick = { hdmiAction(status.uri) }) {
        Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = status.title)
        Text(status.uri.takeLast(1))
    }
}


@Composable
fun VolumeMuteButton(
    volumePresentationModel: VolumePresentationModel,
    muteAction: () -> Unit,
    restoreAction: () -> Unit,
) {
    val modifier = Modifier.fillMaxWidth(.20F)
    return if (null == volumePresentationModel.restoreVolume)
        Button(
            modifier = modifier,
            onClick = muteAction,
            enabled = Volume(0) < volumePresentationModel.targetVolume
        ) {
            Icon(Icons.Filled.VolumeOff, contentDescription = "mute button")
        }
    else
        Button(
            modifier = modifier,
            onClick = restoreAction,
        ) {
            Icon(Icons.Filled.VolumeMute, contentDescription = "restore volume button")
            Text(volumePresentationModel.restoreVolume.value.toString())
        }

}

@Composable
fun VolumeDownButton(volumeDown: () -> Unit, disabled: Boolean = false) {
    val modifier = Modifier.fillMaxWidth(.48F)
    Button(
        modifier = modifier,
        onClick = volumeDown,
        enabled = !disabled
    ) {
        Icon(Icons.Filled.VolumeDown, contentDescription = "volume down")
    }
}

@Composable
fun VolumeUpButton(volumeUp: () -> Unit, disabled: Boolean = false) {
    Button(
        modifier = Modifier.fillMaxWidth(.95F),
        onClick = volumeUp,
        enabled = !disabled
    ) {
        Icon(Icons.Filled.VolumeUp, contentDescription = "volume up")
    }
}
