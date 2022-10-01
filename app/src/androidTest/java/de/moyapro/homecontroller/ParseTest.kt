package de.moyapro.homecontroller

import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.VolumeInformation
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import  org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


@Serializable
data class Example(val param: String)

class ParseTest {

    @Test
    fun parseVolumeInformation() {
        val ex =
            Json.decodeFromString<VolumeInformation>("{\"target\":\"speaker\",\"volume\":3,\"mute\":false,\"maxVolume\":100,\"minVolume\":0}")
        assertNotNull(ex)
    }


    @Test
    fun parseResponseWithVolumeInformation() {
        val expectedVolume = 3
        val ex =
            Json.decodeFromString<VolumeInformationResponse>("""{"result":[[{"target":"speaker","volume":$expectedVolume,"mute":false,"maxVolume":100,"minVolume":0}]],"id":20}""")
        val actualVolume: Int = ex.getVolume()
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }


    @Test
    fun parseResponseWithVolumeInformationWhenTvIsOff() {
        val expectedVolume = 0
        val ex =
            Json.decodeFromString<VolumeInformationResponse>("""{"error":[40005,"Display Is Turned off"],"id":20}""")
        val actualVolume: Int = ex.getVolume()
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }


    @Test
    fun parseResponseWithPowerStatus() {
        val expectedPowerStatus = true
        val ex =
            Json.decodeFromString<PowerStatusResponse>("""{"result":[{"status":"active"}],"id":20}""")
        val actualPowerStatus: Boolean = ex.hasPower()
        assertEquals("Should have got the correct volume", expectedPowerStatus, actualPowerStatus)
    }

}
