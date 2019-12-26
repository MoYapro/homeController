package de.moyapro.homecontroller

import de.moyapro.homecontroller.tvapi.model.VolumeInformation
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.junit.Test


@Serializable
data class Example(val param: String)

class ParseTest {
    @UseExperimental(ImplicitReflectionSerializer::class)
    @Test
    fun parseVolumeInformation() {
        val ex = Json.parse<VolumeInformation>("{\"target\":\"speaker\",\"volume\":3,\"mute\":false,\"maxVolume\":100,\"minVolume\":0}")
    }
}