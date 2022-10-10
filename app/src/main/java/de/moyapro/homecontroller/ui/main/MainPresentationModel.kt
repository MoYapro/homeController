package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.communication.tv.model.HdmiStatusResponse
import de.moyapro.homecontroller.config.getConfiguredJson
import de.moyapro.homecontroller.tv.Volume
import kotlinx.serialization.decodeFromString

data class MainPresentationModel(
    val view: ViewEnum,
    val volume: Volume,
    val hdmiStatus: List<HdmiStatus> = mockHdmiData(),
)

fun mockHdmiData(): List<HdmiStatus> {
    val hdmiStatusResponse: HdmiStatusResponse = getConfiguredJson().decodeFromString("""{
    "result": [
        [
            {
                "uri": "extInput:hdmi?port=1",
                "title": "HDMI 1",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=2",
                "title": "HDMI 2",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=3",
                "title": "HDMI 3/ARC",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:hdmi?port=4",
                "title": "HDMI 4",
                "connection": false,
                "label": "",
                "icon": "meta:hdmi",
                "status": "false"
            },
            {
                "uri": "extInput:composite?port=1",
                "title": "AV",
                "connection": false,
                "label": "",
                "icon": "meta:composite",
                "status": ""
            },
            {
                "uri": "extInput:widi?port=1",
                "title": "Bildschirm spiegeln",
                "connection": true,
                "label": "",
                "icon": "meta:wifidisplay",
                "status": ""
            }
        ]
    ],
    "id": 105
}""")
    return hdmiStatusResponse.result.first()
}
