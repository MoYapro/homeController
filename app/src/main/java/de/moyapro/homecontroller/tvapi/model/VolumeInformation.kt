package de.moyapro.homecontroller.tvapi.model

import kotlinx.serialization.Serializable

//{"result":[[{"target":"speaker","volume":3,"mute":false,"maxVolume":100,"minVolume":0}]],"id":20}

@Serializable
data class VolumeInformation(
    val target: String,
    val volume: Int,
    val mute: Boolean,
    val maxVolume: Int,
    val minVolume: Int
)