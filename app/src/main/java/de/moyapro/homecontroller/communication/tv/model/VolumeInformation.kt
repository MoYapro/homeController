package de.moyapro.homecontroller.communication.tv.model

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInformation(
    val target: String,
    val volume: Int,
    val mute: Boolean,
    val maxVolume: Int,
    val minVolume: Int
)