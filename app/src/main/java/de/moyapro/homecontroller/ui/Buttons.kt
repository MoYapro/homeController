package de.moyapro.homecontroller.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.SettingsInputHdmi
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.TvOff
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.ui.controlls.volume.VolumePresentationModel
import kotlin.time.Duration.Companion.milliseconds

val FAST_BUTTON_DELAY = 250.milliseconds

@Composable
fun OffButton(offAction: () -> Unit, modifier: Modifier = Modifier) {
    MyButton(modifier = modifier, onClick = offAction) {
        Icon(Icons.Outlined.TvOff, contentDescription = "off")
    }
}

@Composable
fun OnButton(onAction: () -> Unit, modifier: Modifier = Modifier) {
    MyButton(
        modifier = modifier,
        onClick = onAction
    ) {
        Icon(Icons.Outlined.Tv, contentDescription = "on")
    }
}

@Composable
fun UpdateAppListButton(onAction: () -> Unit, modifier: Modifier = Modifier) {
    MyButton(
        modifier = modifier,
        onClick = onAction) {
        Icon(Icons.Outlined.Update, contentDescription = "on")
    }
}

@Composable
fun SettingsButton(modifier: Modifier, openSettings: () -> Unit) {
    MyButton(
        modifier = modifier,
        onClick = openSettings
    ) {
        Icon(Icons.Filled.Settings, contentDescription = "settings")
    }
}

@Composable
fun MainButton(modifier: Modifier = Modifier, openMain: () -> Unit) {
    MyButton(
        modifier = modifier,
        onClick = openMain
    ) {
        Icon(Icons.Filled.Domain, contentDescription = "main")
    }
}


@Composable
fun BackButton(backAction: () -> Unit) {
    MyButton(modifier = Modifier.fillMaxWidth(.48F), onClick = backAction) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Composable
fun HomeButton(homeAction: () -> Unit) {
    MyButton(
        modifier = Modifier.fillMaxWidth(.95F),
        onClick = homeAction
    ) {
        Icon(Icons.Filled.Home, contentDescription = "Home")
    }
}


@Composable
fun DownButton(downAction: () -> Unit) {
    RepeatingButton(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        onClick = downAction,
        maxDelay = FAST_BUTTON_DELAY,
    ) {
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down", Modifier.size(75.dp))
    }
}

@Composable
fun RightButton(rightAction: () -> Unit) {
    RepeatingButton(
        modifier = Modifier
            .fillMaxWidth(1F)
            .fillMaxHeight(.5F)
            .padding(start = 5.dp),
        onClick = rightAction,
        maxDelay = FAST_BUTTON_DELAY,
    ) {
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Right", Modifier.size(75.dp))
    }
}

@Composable
fun OkButton(okAction: () -> Unit) {
    RepeatingButton(
        modifier = Modifier
            .fillMaxWidth(.5F)
            .fillMaxHeight(.5F)
            .padding(horizontal = 5.dp),
        onClick = okAction,
        maxDelay = FAST_BUTTON_DELAY,
    ) {
        Text("OK", fontSize = 20.sp)
    }
}

@Composable
fun LeftButton(leftAction: () -> Unit) {
    RepeatingButton(
        modifier = Modifier
            .fillMaxWidth(.33F)
            .fillMaxHeight(.5F)
            .padding(end = 5.dp),
        onClick = leftAction,
        maxDelay = FAST_BUTTON_DELAY,
    ) {
        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Left", Modifier.size(75.dp))
    }
}

@Composable
fun UpButton(upAction: () -> Unit) {
    RepeatingButton(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        onClick = upAction,
        maxDelay = FAST_BUTTON_DELAY,
    ) {
        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up", Modifier.size(75.dp))
    }
}


@Composable
fun HdmiButton(modifier: Modifier, status: HdmiStatus, hdmiAction: (uri: String) -> Unit) {
    MyButton(
        modifier = modifier,
        enabled = true,
        onClick = { hdmiAction(status.uri) },
        colors = ButtonDefaults.buttonColors(
            contentColor = if (status.connection) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.primary
        ),
    ) {
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
    val modifier = Modifier.fillMaxWidth(.23F)
    return if (null == volumePresentationModel.restoreVolume)
        MyButton(
            modifier = modifier,
            onClick = muteAction,
            enabled = !volumePresentationModel.muteDisabled
        ) {
            Icon(Icons.Filled.VolumeOff, contentDescription = "mute button")
        }
    else
        MyButton(
            modifier = modifier,
            onClick = restoreAction,
            enabled = !volumePresentationModel.muteDisabled
        ) {
            Icon(Icons.Filled.VolumeMute, contentDescription = "restore volume button")
            Text(volumePresentationModel.restoreVolume.value.toString())
        }

}

@Composable
fun VolumeDownButton(
    volumeDown: () -> Unit,
    applyTargetVolumeToTv: () -> Unit,
    disabled: Boolean = false,
) {
    val modifier = Modifier.fillMaxWidth(.48F)
    RepeatingButton(
        modifier = modifier,
        onClick = volumeDown,
        onRelease = applyTargetVolumeToTv,
        enabled = !disabled
    ) {
        Icon(Icons.Filled.VolumeDown, contentDescription = "volume down")
    }
}

@Composable
fun VolumeUpButton(
    volumeUpAction: () -> Unit,
    applyTargetVolumeToTv: () -> Unit,
    disabled: Boolean = false,
) {

    RepeatingButton(
        modifier = Modifier.fillMaxWidth(.95F),
        onClick = volumeUpAction,
        onRelease = applyTargetVolumeToTv,
        enabled = !disabled
    ) {
        Icon(Icons.Filled.VolumeUp, contentDescription = "volume up")
    }
}
