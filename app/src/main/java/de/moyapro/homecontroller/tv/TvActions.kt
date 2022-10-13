package de.moyapro.homecontroller.tv

data class TvActions(
    val onAction: () -> Unit,
    val offAction: () -> Unit,
    val updatePowerStatus: () -> Unit,
    val setVolume: (volume: Volume) -> Unit,
    val volumeUp: () -> Unit,
    val volumeDown: () -> Unit,
    val backAction: () -> Unit,
    val leftAction: () -> Unit,
    val okAction: () -> Unit,
    val rightAction: () -> Unit,
    val downAction: () -> Unit,
    val upAction: () -> Unit,
    val homeAction: () -> Unit,
    val setHdmiAction: (String) -> Unit,
)
