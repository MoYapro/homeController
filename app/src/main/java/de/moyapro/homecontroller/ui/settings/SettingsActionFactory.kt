package de.moyapro.homecontroller.ui.settings

import android.content.SharedPreferences

fun buildSettingsActions(preferences: SharedPreferences): SettingsActions {
    return SettingsActions(
        readSettings = { setting -> preferences.getString(setting.name, "") ?: "" },
        updateSetting = { setting, newValue ->
            preferences.edit().putString(setting.name, newValue).apply()
        }
    )
}
