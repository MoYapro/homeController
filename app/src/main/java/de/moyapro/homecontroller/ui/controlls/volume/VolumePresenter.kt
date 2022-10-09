package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

object VolumePresenter {
    fun present(tvVolume: Volume, volumeState: VolumeState): VolumePresentationModel {
        return VolumePresentationModel(
            tvVolume = tvVolume,
            targetVolume = volumeState.targetVolume,
            upDisabled = volumeState.targetVolume >= VolumeConstants.MAX_VOLUME,
            downDisabled = volumeState.targetVolume <= VolumeConstants.MIN_VOLUME
        )
    }

}
