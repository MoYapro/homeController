package de.moyapro.homecontroller.communication.tv

enum class TvStatusEnum(val content: String, val urlGenerator: (String) -> String) {
    POWER_STATUS(
        "{\"id\": 20, \"method\": \"getPowerStatus\", \"version\": \"1.0\", \"params\": [] }",
        { ip: String -> "http://${ip}/sony/system" }
    ),
    VOLUME_STATUS(
        "{\"id\": 20, \"method\": \"getVolumeInformation\", \"version\": \"1.0\", \"params\": [] }",
        { ip: String -> "http://${ip}/sony/audio" }
    )

}