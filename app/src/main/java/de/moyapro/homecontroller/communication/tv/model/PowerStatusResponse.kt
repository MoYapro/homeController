package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class PowerStatusResponse(
    val result: List<PowerStatus>,
    val id: Int
) {
    fun hasPower(): Boolean {
        return "active" == result[0].status
    }
}
