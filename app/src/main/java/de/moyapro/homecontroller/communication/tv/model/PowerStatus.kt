package de.moyapro.homecontroller.communication.tv.model

import de.moyapro.homecontroller.tv.Volume

data class PowerStatus(val status: PowerStatusEnum)

enum class PowerStatusEnum(val stringValue: String) {
    STANDBY("standby"),
    ON("active")
}

fun getPowerStatusValueFor(value: String) = when (value) {
    "active" -> PowerStatusEnum.ON
    "standby" -> PowerStatusEnum.STANDBY
    else -> throw IllegalArgumentException("Cannot determin powerstatus for value $value")
}
