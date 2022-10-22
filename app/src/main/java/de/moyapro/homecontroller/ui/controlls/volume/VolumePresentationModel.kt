package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

data class VolumePresentationModel(
    val tvVolume: Volume = Volume(0),
    val targetVolume: Volume = Volume(0),
    val volumeChangeText: String? = "",
    val restoreVolume: Volume? = null,
    val downDisabled: Boolean = true,
    val upDisabled: Boolean = true,
    val muteDisabled: Boolean = false,
)
