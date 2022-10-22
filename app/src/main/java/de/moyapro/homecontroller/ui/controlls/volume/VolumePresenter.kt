package de.moyapro.homecontroller.ui.controlls.volume

import de.moyapro.homecontroller.tv.Volume

object VolumePresenter {
    const val TAG = "VolumePresenter"

    fun present(
        tvVolume: Volume,
        volumeState: VolumeState
    ): VolumePresentationModel {
        val targetVolume = volumeState.targetVolume ?: tvVolume
        return VolumePresentationModel(
            tvVolume = tvVolume,
            targetVolume = targetVolume ?: tvVolume,
            downDisabled = targetVolume <= VolumeConstants.MIN_VOLUME,
            upDisabled = targetVolume >= VolumeConstants.MAX_VOLUME,
            volumeChangeText = buildVolumeChangeText(tvVolume, targetVolume),
            restoreVolume = volumeState.restoreVolume,
        )
    }

    private fun buildVolumeChangeText(tvVolume: Volume, targetVolume: Volume): String? {
        if (tvVolume == targetVolume) return null
        return "${tvVolume.value} -> ${targetVolume.value}"
    }

}
