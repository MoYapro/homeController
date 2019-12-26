package de.moyapro.homecontroller

import de.moyapro.homecontroller.communication.tv.model.PowerStatusResponse
import de.moyapro.homecontroller.communication.tv.model.VolumeInformation
import de.moyapro.homecontroller.communication.tv.model.VolumeInformationResponse
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


@Serializable
data class Example(val param: String)

class ParseTest {
    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseVolumeInformation() {
        val ex =
            Json.parse<VolumeInformation>("{\"target\":\"speaker\",\"volume\":3,\"mute\":false,\"maxVolume\":100,\"minVolume\":0}")
        assertNotNull(ex)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseResponseWithVolumeInformation() {
        val expectedVolume = 3
        val ex =
            Json.parse<VolumeInformationResponse>("""{"result":[[{"target":"speaker","volume":$expectedVolume,"mute":false,"maxVolume":100,"minVolume":0}]],"id":20}""")
        val actualVolume: Int = ex.getVolume()
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseResponseWithPowerStatus() {
        val expectedPowerStatus = true
        val ex =
            Json.parse<PowerStatusResponse>("""{"result":[{"status":"active"}],"id":20}""")
        val actualPowerStatus: Boolean = ex.hasPower()
        assertEquals("Should have got the correct volume", expectedPowerStatus, actualPowerStatus)
    }

}