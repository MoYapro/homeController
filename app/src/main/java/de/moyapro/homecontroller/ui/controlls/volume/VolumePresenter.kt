package de.moyapro.homecontroller.ui.controlls.volume

import android.util.Log
import de.moyapro.homecontroller.tv.Volume

object VolumePresenter {
    const val TAG = "VolumePresenter"

    fun present(tvVolume: Volume, volumeState: VolumeState): VolumePresentationModel {
        Log.i(TAG, "present state $volumeState")
        return VolumePresentationModel(
            tvVolume = tvVolume,
            targetVolume = volumeState.targetVolume,
            downDisabled = volumeState.targetVolume <= VolumeConstants.MIN_VOLUME,
            upDisabled = volumeState.targetVolume >= VolumeConstants.MAX_VOLUME,
            volumeChangeText = buildVolumeChangeText(tvVolume, volumeState.targetVolume),
            restoreVolume = volumeState.restoreVolume
        )
    }

    private fun buildVolumeChangeText(tvVolume: Volume, targetVolume: Volume): String? {
        if (tvVolume == targetVolume) return null
        return "${tvVolume.value} -> ${targetVolume.value}"
    }

}
