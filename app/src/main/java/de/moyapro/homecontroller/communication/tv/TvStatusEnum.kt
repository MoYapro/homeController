package de.moyapro.homecontroller.communication.tv

enum class TvStatusEnum(val content: String, val url: String) {
    POWER_STATUS(
        "{\"id\": 20, \"method\": \"getPowerStatus\", \"version\": \"1.0\", \"params\": [] }",
        "http://192.168.1.111/sony/system"
    ),
    VOLUME_STATUS(
        "{\"id\": 20, \"method\": \"getVolumeInformation\", \"version\": \"1.0\", \"params\": [] }",
        "http://192.168.1.111/sony/audio"
    )

}