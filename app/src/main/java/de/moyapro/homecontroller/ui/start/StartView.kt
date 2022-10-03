package de.moyapro.homecontroller.ui.start

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.homecontroller.tv.TvActions
import de.moyapro.homecontroller.ui.OffButton
import de.moyapro.homecontroller.ui.OnButton
import de.moyapro.homecontroller.ui.SettingsButton

@Composable
fun StartView(actions: TvActions) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { OffButton(actions.offAction) },
        bottomBar = { SettingsButton() }) {
        OnButton(actions.onAction)
    }
}
