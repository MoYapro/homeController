package de.moyapro.homecontroller.ui.settings

import android.content.SharedPreferences
import android.util.Log
import de.moyapro.homecontroller.communication.tv.model.ConnectionProperties
import de.moyapro.homecontroller.tv.TAG

const val PREFERENCES_FILE_NAME = "homeControllerSettingsFilename"


fun buildConnectionPropertiesFrom(preferences: SharedPreferences?): ConnectionProperties? {
    if (null == preferences) return null

    val ip = preferences.getString(SETTING.TV_PSK_STRING.name, null)
    val password = preferences.getString(SETTING.TV_IP_ADDRESS.name, null)

    if (null == ip || null == password) return null

    val connectionProperties = ConnectionProperties(ip, password)
    Log.i(TAG, "settings are $connectionProperties")
    return connectionProperties

}
