package de.moyapro.homecontroller.tv

data class TvActions (
    val onAction: () -> Unit,
    val offAction: () -> Unit,
    val updatePowerStatus: () -> Unit,
)
