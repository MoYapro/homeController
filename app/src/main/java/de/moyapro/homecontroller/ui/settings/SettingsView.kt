package de.moyapro.homecontroller.ui.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.homecontroller.ui.SettingsButton

@Composable
fun SettingsRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        SettingsButton()
    }
}
