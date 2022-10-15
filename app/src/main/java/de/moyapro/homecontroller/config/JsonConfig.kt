package de.moyapro.homecontroller.config

import kotlinx.serialization.json.Json

fun getConfiguredJson(): Json {
    return Json {
        ignoreUnknownKeys = true
        coerceInputValues = false
        isLenient = true
    }
}
