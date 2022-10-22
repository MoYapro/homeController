package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

data class VolumeState(
    val targetVolume: Volume? = null,
    val restoreVolume: Volume? = null
) {
}
