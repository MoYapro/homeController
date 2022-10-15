package de.moyapro.homecontroller.communication.tv

enum class TVCommandEnum(val contentGenerator: (String) -> String, val urlGenerator: (String) -> String) {
    POWER(
        { value: String -> "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"status\":${value}}]}" },
        { ip: String -> "http://${ip}/sony/system" }
    ),
    VOLUME(
        { value: String -> "{\"method\":\"setAudioVolume\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"target\":\"speaker\",\"volume\":\"$value\"}]}" },
        { ip: String -> "http://${ip}/sony/audio" }
    ),
    HDMI(
        { value: String -> "{\"method\":\"setPlayContent\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"uri\":\"$value\"}]}" },
        { ip: String -> "http://${ip}/sony/avContent" }
    ),
    IRCC(
        { value: String ->
            """|<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" s:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
               |<s:Body>
               |    <u:X_SendIRCC xmlns:u="urn:schemas-sony-com:service:IRCC:1">
               |<IRCCCode>$value</IRCCCode>
               |    </u:X_SendIRCC>
               |</s:Body>
               |</s:Envelope>""".trimMargin()
        },
        { ip: String -> "http://${ip}/sony/IRCC" }
    );

    operator fun invoke() {}

}
