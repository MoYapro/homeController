package de.moyapro.homecontroller.config

import kotlinx.serialization.json.Json

fun getConfiguredJson(): Json {
    return Json {
        ignoreUnknownKeys = false
        coerceInputValues = false
        isLenient = true
    }
}
