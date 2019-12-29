package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInformationResponse(
    val result: List<List<VolumeInformation>> = listOf(),
    val error: List<String> = listOf(),
    val id: Int
) {
    fun getVolume(): Int {
        if (result.isNotEmpty()) {
            return result[0][0].volume
        }
        return 0
    }
}