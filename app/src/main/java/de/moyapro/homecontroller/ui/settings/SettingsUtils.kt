package de.moyapro.homecontroller.ui.settings

import android.content.SharedPreferences
import de.moyapro.homecontroller.communication.tv.SettingsKeys
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties

const val PREFERENCES_FILE_NAME = "homeControllerSettingsFilename"


fun buildConnectionPropertiesFrom(preferences: SharedPreferences?): ConnectionProperties? {
    if (null == preferences) return null

    val ip = preferences.getString(SettingsKeys.IP, "127.0.0.1")!!
    val password = preferences.getString(SettingsKeys.PASSWORD, "invalid")!!
    return ConnectionProperties("192.168.1.111", "xxx")
}
