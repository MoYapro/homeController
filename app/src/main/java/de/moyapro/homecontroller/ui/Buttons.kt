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
fun BackButton() {
    Button(modifier = Modifier.fillMaxWidth(.5F), onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Composable
fun HomeButton() {
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Home, contentDescription = "Home")
    }
}


@Composable
fun DownButton() {
    Button(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Down")
    }
}

@Composable
fun RightButton() {
    Button(modifier = Modifier
        .fillMaxWidth(1F)
        .fillMaxHeight(.5F),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Right")
    }
}

@Composable
fun OkButton() {
    Button(modifier = Modifier
        .fillMaxWidth(.5F)
        .fillMaxHeight(.5F),
        onClick = { /*TODO*/ }) {
        Text("OK")
    }
}

@Composable
fun LeftButton() {
    Button(modifier = Modifier
        .fillMaxWidth(.33F)
        .fillMaxHeight(.5F),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Left")
    }
}

@Composable
fun UpButton() {
    Button(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Up")
    }
}


@Composable
fun Hdmi1Button(modifier: Modifier) {
    Button(modifier = modifier,
        onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 1")
        Text("1")
    }
}


@Composable
fun Hdmi2Button(modifier: Modifier) {
    Button(modifier = modifier,
        onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 2")
        Text("2")
    }
}

@Composable
fun Hdmi3Button(modifier: Modifier) {
    Button(modifier = modifier,
        onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 3")
        Text("3")
    }
}

@Composable
fun Hdmi4Button(modifier: Modifier) {
    Button(modifier = modifier,
        onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.SettingsInputHdmi, contentDescription = "H D M I 4")
        Text("4")
    }
}


@Composable
fun VolumeDownButton() {
    Button(modifier = Modifier.fillMaxWidth(.5F),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.VolumeDown, contentDescription = "volume down")
    }
}

@Composable
fun VolumeUpButton() {
    Button(modifier = Modifier.fillMaxWidth(),
        onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.VolumeUp, contentDescription = "volume up")
    }
}
