package de.moyapro.homecontroller.communication.tv.model

@kotlinx.serialization.Serializable
data class HdmiStatusResponse(
    val id: Long?,
    val result: List<List<HdmiStatus>>,
)

@kotlinx.serialization.Serializable
data class AppListResponse(
    val id: Long?,
    val result: List<List<AppInfo>>
)

@kotlinx.serialization.Serializable
data class AppInfo(val title: String, val uri: String, val icon: String)

@kotlinx.serialization.Serializable
data class HdmiStatus(
    val uri: String,
    val title: String,
    val connection: Boolean,
    val label: String,
    val icon: String,
    val status: String,
)
