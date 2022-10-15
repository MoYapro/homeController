package de.moyapro.homecontroller.ui.settings

data class SettingsActions(
    val readSettings: (SETTING) -> String,
    val updateSetting: (SETTING, String) -> Unit,
)
