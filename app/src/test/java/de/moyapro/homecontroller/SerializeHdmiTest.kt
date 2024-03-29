package de.moyapro.homecontroller

import de.moyapro.homecontroller.communication.tv.model.HdmiStatus
import de.moyapro.homecontroller.communication.tv.model.HdmiStatusResponse
import de.moyapro.homecontroller.config.getConfiguredJson
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import org.junit.Test

class SerializeHdmiTest {

    val hdmiStatusResponseExample = """{
    "result": [
        [
            {
                "uri": "extInput:cec?type=player&port=1&logicalAddr=4",
                "title": "NintendoSwitch",
                "connection": true,
                "label": "",
                "icon": "meta:playbackdevice",
                "status": ""
            },
            {
                "uri": "extInput:hdmi?port=1",
                "title": "HDMI 1",
                "connection": true,
                "label": "",
                "icon": "meta:hdmi",
                "status": "true"
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
}"""


    @Test
    fun parseHdmiJson() {
        val result: HdmiStatusResponse =
            getConfiguredJson().decodeFromString(hdmiStatusResponseExample)
        result.result.first() shouldHaveSize 7
        result.result.first().filter { it.uri.contains("hdmi") } shouldHaveSize 4
        result.result.first().filter { it.title.contains("Switch") } shouldHaveSize 1
        result.result.first().filter { it.uri.contains("composite") } shouldHaveSize 1
        result.result.first().filter { it.uri.contains("widi") } shouldHaveSize 1
        result.result.first()
            .single { it.uri.contains("extInput:hdmi?port=2") } shouldBe HdmiStatus(
            uri = "extInput:hdmi?port=2",
            title = "HDMI 2",
            connection = false,
            label = "",
            icon = "meta:hdmi",
            status = "false"
        )
    }

}
