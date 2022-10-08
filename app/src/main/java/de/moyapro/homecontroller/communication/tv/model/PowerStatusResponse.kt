package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class PowerStatusResponse(
    val result: List<PowerStatusResponseValue>,
    val id: Int,
) {
    fun hasPower(): Boolean {
        return "active" == result.firstOrNull()?.status
    }
}

@Serializable
data class PowerStatusResponseValue(val status: String)
