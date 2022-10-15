package de.moyapro.homecontroller.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun SettingsView(settingsActions: SettingsActions) {
    Column() {
        SETTING.values().forEach { setting ->
            StringSettings(setting, settingsActions)
        }
    }
}
