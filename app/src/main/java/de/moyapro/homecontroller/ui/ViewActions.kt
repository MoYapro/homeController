package de.moyapro.homecontroller.ui

data class ViewActions(
    val onAction: () -> Unit,
    val offAction: () -> Unit,
    val updatePowerStatus: () -> Unit,
) {
}
