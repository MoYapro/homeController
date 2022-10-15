package de.moyapro.homecontroller.ui.settings

enum class SETTING(val label: String, val description: String) {
    TV_IP_ADDRESS("TV ip address", "Set the ip of your TV to make the app send commands to it"),
    TV_PSK_STRING("TV password (PSK)",
        "The TV needs to have a password (PSK) set and the same password has to be entered here"),
}
