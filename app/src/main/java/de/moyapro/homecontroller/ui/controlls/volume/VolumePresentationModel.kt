package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

data class VolumePresentationModel(
    val tvVolume: Volume,
    val targetVolume: Volume,
    val downDisabled: Boolean,
    val upDisabled: Boolean,
    val volumeChangeText: String?,
)
