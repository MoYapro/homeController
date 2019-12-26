package de.moyapro.homecontroller.tvapi.model

import kotlinx.serialization.Serializable

@Serializable
data class TvResponse(
    val result: List<List<VolumeInformation>>,
    val id: Int
)