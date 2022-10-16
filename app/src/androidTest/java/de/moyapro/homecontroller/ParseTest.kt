package de.moyapro.homecontroller

import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.VolumeInformation
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import de.moyapro.homecontroller.config.getConfiguredJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import  org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


@Serializable
data class Example(val param: String)

class ParseTest {

    @Test
    fun parseVolumeInformation() {
        val ex =
            getConfiguredJson().decodeFromString<VolumeInformation>("{\"target\":\"speaker\",\"volume\":3,\"mute\":false,\"maxVolume\":100,\"minVolume\":0}")
        assertNotNull(ex)
    }


    @Test
    fun parseResponseWithVolumeInformation() {
        val jsonString =
            """{"result":[[{"target":"speaker","volume":7,"mute":false,"maxVolume":100,"minVolume":0}]],"id":20}"""
        val expectedVolume = 7
        val result = getConfiguredJson().decodeFromString<VolumeInformationResponse>(jsonString)
        val actualVolume: Int = result.getVolume()
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }


    @Test
    fun parseResponseWithVolumeInformationWhenTvIsOff() {
        val expectedVolume = 0
        val result =
            getConfiguredJson().decodeFromString<VolumeInformationResponse>("""{"error":[40005,"Display Is Turned off"],"id":20}""")
        val actualVolume: Int = result.getVolume()
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }


    @Test
    fun parseResponseWithPowerStatus() {
        val expectedPowerStatus = "active"
        val result =
            getConfiguredJson().decodeFromString<PowerStatusResponse>("""{"result":[{"status":"active"}],"id":20}""")
        val actualPowerStatus = result.result.first().first().status
        assertEquals("Should have got the correct power status",
            expectedPowerStatus,
            actualPowerStatus)
    }

}
