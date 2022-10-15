package de.moyapro.homecontroller.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import de.moyapro.homecontroller.util.MUTED_ALPHA

@Composable
fun StringSettings(
    setting: SETTING,
    settingsActions: SettingsActions,
) {
    var currentValue: String by remember {
        mutableStateOf(settingsActions.readSettings(setting))
    }
    var isFocused: Boolean by remember { mutableStateOf(false) }

    Surface() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Column(Modifier.fillMaxWidth()) {
                EditTextField(initialValue = currentValue,
                    label = setting.label,
                    onValueChange = { newValue: String ->
                        currentValue = newValue
                        settingsActions.updateSetting(setting, newValue)
                    },
                    onFocusChanged = { focusState -> isFocused = focusState.isFocused }
                )
                if (isFocused) {
                    Text(text = setting.description, modifier = Modifier.alpha(MUTED_ALPHA))
                }
            }
        }
    }

}
