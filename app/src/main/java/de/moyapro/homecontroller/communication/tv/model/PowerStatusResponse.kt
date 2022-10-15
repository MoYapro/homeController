package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class PowerStatusResponse(
    val result: List<List<PowerStatusResponseValue>>,
    val id: Int,
)

@Serializable
data class PowerStatusResponseValue(val status: String)
