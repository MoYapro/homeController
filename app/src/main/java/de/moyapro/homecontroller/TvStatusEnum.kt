package de.moyapro.homecontroller.de.moyapro.homeController

enum class TvStatusEnum(val content: String, val url: String) {
    POWER_STATUS(
        "{\"id\": 20, \"method\": \"getPowerStatus\", \"version\": \"1.0\", \"params\": [] }",
        "http://192.168.1.111/sony/system"
    )
}