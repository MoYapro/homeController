package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

data class VolumeState(
    val targetVolume: Volume,
    val restoreVolume: Volume?
) {
}
