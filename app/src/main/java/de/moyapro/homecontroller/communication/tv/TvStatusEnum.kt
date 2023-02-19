package de.moyapro.homecontroller.communication.tv

enum class TvStatusEnum(val content: String, val urlGenerator: (String) -> String) {
    POWER_STATUS(
        """{"id": 20, "method": "getPowerStatus", "version": "1.0", "params": [] }""",
        { ip: String -> "http://$ip/sony/system" }
    ),
    VOLUME_STATUS(
        """{"id": 20, "method": "getVolumeInformation", "version": "1.0", "params": [] }""",
        { ip: String -> "http://$ip/sony/audio" }
    ),
    HDMI_STATUS(
        """{"method": "getCurrentExternalInputsStatus", "id": 105, "params": [], "version": "1.1"}""",
        { ip: String -> "http://$ip/sony/avContent" }
    ),
    APPLIST(
        """{"method":"getApplicationList","params":[],"id":1,"version":"1.0"}""",
        { ip: String -> "http://${ip}/sony/appControl" }
    ),

}
