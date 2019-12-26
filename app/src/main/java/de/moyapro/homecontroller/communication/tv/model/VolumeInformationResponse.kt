package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInformationResponse(
    val result: List<List<VolumeInformation>>,
    val id: Int
) {
    fun getVolume(): Int {
        return result[0][0].volume
    }
}