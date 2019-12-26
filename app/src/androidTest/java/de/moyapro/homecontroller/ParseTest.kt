package de.moyapro.homecontroller

import de.moyapro.homecontroller.tvapi.model.TvResponse
import de.moyapro.homecontroller.tvapi.model.VolumeInformation
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.junit.Test
import org.junit.Assert.*


@Serializable
data class Example(val param: String)

class ParseTest {
    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseVolumeInformation() {
        val ex =
            Json.parse<VolumeInformation>("{\"target\":\"speaker\",\"volume\":3,\"mute\":false,\"maxVolume\":100,\"minVolume\":0}")
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseResponseWithVolumeInformation() {
        val expectedVolume = 3
        val ex =
            Json.parse<TvResponse>("""{"result":[[{"target":"speaker","volume":$expectedVolume,"mute":false,"maxVolume":100,"minVolume":0}]],"id":20}""")
        val actualVolume: Int = ex.result[0][0].volume
        assertEquals("Should have got the correct volume", expectedVolume, actualVolume)
    }

}