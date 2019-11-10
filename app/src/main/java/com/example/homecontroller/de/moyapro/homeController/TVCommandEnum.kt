package com.example.homecontroller.de.moyapro.homeController

enum class TVCommandEnum(val contentGenerator: (String) -> String, val url: String) {
    POWER(
        { value: String -> "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"status\":${value}}]}" },
        "http://192.168.1.111/sony/system"
    ),
    VOLUME(
        { value: String -> "{\"method\":\"setAudioVolume\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"target\":\"speaker\",\"volume\":\"$value\"}]}" },
        "http://192.168.1.111/sony/audio"
    ),
    HDMI(
        { value: String -> "{\"method\":\"setPlayContent\",\"version\":\"1.0\",\"id\":1,\"params\":[{\"uri\":\"extInput:hdmi?port=$value\"}]}" },
        "http://192.168.1.111/sony/avContent"
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
        "http://192.168.1.111/sony/IRCC"
    )
}