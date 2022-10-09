package de.moyapro.homecontroller.ui.main

import de.moyapro.homecontroller.tv.Volume

data class MainPresentationModel(
    val view: ViewEnum,
    val volume: Volume
)
